package hc.fcdr.rws.importer;

import java.util.ArrayList;
import java.util.List;

public class ImportStatistics
{
    private String                      importDateTime;
    private Long                        totalLoadingTime;
    private Long                        totalProcessingTime;
    private Integer                     numberOfRecordsProcessed;
    private Integer                     numberOfInvalidRecords;
    private List<ImportReportDetailRow> importReportDetailRowList;

    public ImportStatistics()
    {
        super();
        importDateTime = "";
        totalLoadingTime = 0L;
        totalProcessingTime = 0L;
        numberOfRecordsProcessed = 0;
        numberOfInvalidRecords = 0;
        importReportDetailRowList = new ArrayList<ImportReportDetailRow>();
    }

    public String getImportDateTime()
    {
        return importDateTime;
    }

    public void setImportDateTime(final String importDateTime)
    {
        this.importDateTime = importDateTime;
    }

    public Long getTotalLoadingTime()
    {
        return totalLoadingTime;
    }

    public void setTotalLoadingTime(final Long totalLoadingTime)
    {
        this.totalLoadingTime = totalLoadingTime;
    }

    public Long getTotalProcessingTime()
    {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(final Long totalProcessingTime)
    {
        this.totalProcessingTime = totalProcessingTime;
    }

    public Integer getNumberOfRecordsProcessed()
    {
        return numberOfRecordsProcessed;
    }

    public void setNumberOfRecordsProcessed(
            final Integer numberOfRecordsProcessed)
    {
        this.numberOfRecordsProcessed = numberOfRecordsProcessed;
    }

    public Integer getNumberOfInvalidRecords()
    {
        return numberOfInvalidRecords;
    }

    public void setNumberOfInvalidRecords(final Integer numberOfInvalidRecords)
    {
        this.numberOfInvalidRecords = numberOfInvalidRecords;
    }

    public List<ImportReportDetailRow> getImportReportDetailRowList()
    {
        return importReportDetailRowList;
    }

    public void setImportReportDetailRowList(
            final List<ImportReportDetailRow> importReportDetailRowList)
    {
        this.importReportDetailRowList = importReportDetailRowList;
    }

    @Override
    public String toString()
    {
        return "ImportStatistics [importDateTime="
                + importDateTime + ", totalLoadingTime=" + totalLoadingTime
                + ", totalProcessingTime=" + totalProcessingTime
                + ", numberOfRecordsProcessed=" + numberOfRecordsProcessed
                + ", numberOfInvalidRecords=" + numberOfInvalidRecords
                + ", importReportDetailRowList=" + importReportDetailRowList
                + "]";
    }

}
