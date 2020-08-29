package org.jasig.ssp.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.JournalEntryDao;
import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;
import org.jasig.ssp.transferobject.reports.JournalCaseNotesStudentReportTO;
import org.jasig.ssp.transferobject.reports.JournalStepSearchFormTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JournalCaseNoteStudentReportService {

    //1
    private final JournalEntryDao journalDao;
    //1
    private final PersonDao personDao;

    public JournalCaseNoteStudentReportService(JournalEntryDao journalEntryDao, PersonDao personDao) {
        this.journalDao = journalEntryDao;
        this.personDao = personDao;
    }

    //1
    public List<JournalCaseNotesStudentReportTO> getFromCriteria(JournalStepSearchFormTO personSearchForm, SortingAndPaging sAndP) throws ObjectNotFoundException {
        final List<JournalCaseNotesStudentReportTO> personsWithJournalEntries = journalDao.getJournalCaseNoteStudentReportTOsFromCriteria(personSearchForm, sAndP);
        final Map<String, JournalCaseNotesStudentReportTO> map = new HashMap<String, JournalCaseNotesStudentReportTO>();

        //1
        for (JournalCaseNotesStudentReportTO entry : personsWithJournalEntries) {
            map.put(entry.getSchoolId(), entry);
        }

        final SortingAndPaging personSAndP = SortingAndPaging.createForSingleSortAll(ObjectStatus.ACTIVE, "lastName", "DESC");
        //1
        final PagingWrapper<BaseStudentReportTO> persons = personDao.getStudentReportTOs(personSearchForm, personSAndP);

        //1
        if (persons == null) {
            return personsWithJournalEntries;
        }

        //1
        for (BaseStudentReportTO person : persons) {
            //1
            if ((!map.containsKey(person.getSchoolId()) && StringUtils.isNotBlank(person.getCoachSchoolId()))
                    && hasJournalForPersonForJournalSourceIds(personSearchForm, person)) {
                final JournalCaseNotesStudentReportTO entry = new JournalCaseNotesStudentReportTO(person);
                personsWithJournalEntries.add(entry);
                map.put(entry.getSchoolId(), entry);
            }
        }

        Collections.sort(personsWithJournalEntries);

        return personsWithJournalEntries;
    }

    private boolean hasJournalForPersonForJournalSourceIds(JournalStepSearchFormTO personSearchForm, BaseStudentReportTO person) {
        return personSearchForm.getJournalSourceIds() != null
                && journalDao.getJournalCountForPersonForJournalSourceIds(person.getId(), personSearchForm.getJournalSourceIds()) > 0;
    }
}
