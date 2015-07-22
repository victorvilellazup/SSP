package org.jasig.ssp.web.api.reports;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reports.JournalCaseNotesStudentReportTO;
import org.jasig.ssp.transferobject.reports.JournalStepSearchFormTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/1/report/journalcasenotesbystudent")
public class JournalCaseNotesByStudentController extends ReportBaseController<JournalCaseNotesStudentReportTO> {
	private static String REPORT_URL = "/reports/journalCaseNotesByStudent.jasper";
	private static String REPORT_FILE_TITLE = "Journal_Case_Notes_Report";

	@Autowired
	JournalEntryService journalEntryService;
	
	@Autowired
	JournalReportParameters journalReportParameters; 
	
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT,
				Locale.US);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getJournalCaseNotesByStudentReport(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) UUID coachId,
			final @RequestParam(required = false) UUID programStatus,	
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) List<UUID> serviceReasonIds,
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false) String homeDepartment,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, JRException, IOException {
		
		final Map<String, Object> parameters = Maps.newHashMap();
		final JournalStepSearchFormTO personSearchForm = new JournalStepSearchFormTO();
		
		journalReportParameters.setParameters( status,
				parameters, 
				personSearchForm,
				coachId,
				programStatus,	
				false,
				specialServiceGroupIds,
				studentTypeIds,
				serviceReasonIds,
				null,
				createDateFrom,
				createDateTo,
				termCode,
				homeDepartment);
		final List<JournalCaseNotesStudentReportTO> reports = (List<JournalCaseNotesStudentReportTO>)
				Lists.newArrayList(journalEntryService.getJournalCaseNoteStudentReportTOsFromCriteria(
				personSearchForm, SearchParameters.getReportPersonSortingAndPagingAll(status,"person")));
		
		SearchParameters.addStudentCount(reports, parameters);
		renderReport( response,  parameters, reports,  REPORT_URL, reportType, REPORT_FILE_TITLE);
	}
}
