package org.jasig.ssp.util;

import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;

public class BaseStudentReportNameComparator {
    public static int compare(BaseStudentReportTO p1, BaseStudentReportTO p2){
        int value=p1.getLastName().compareToIgnoreCase(
                p2.getLastName());

        //1
        if(value!=0)
            return value;

        value=p1.getFirstName().compareToIgnoreCase(
                p2.getFirstName());
        //1
        if(value!=0)
            return value;
        //1
        if(p1.getMiddleName()==null&&p2.getMiddleName()==null)
            return 0;
        //1
        if(p1.getMiddleName()==null)
            return-1;
        //1
        if(p2.getMiddleName()==null)
            return 1;

        return p1.getMiddleName().compareToIgnoreCase(
                p2.getMiddleName());
    }
}
