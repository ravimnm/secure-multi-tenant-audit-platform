package com.ivar.audit.modules.dto;

public record IntegrityReportDto(

        boolean verified,

        long checkedLogs,

        long failedLogs

) {}