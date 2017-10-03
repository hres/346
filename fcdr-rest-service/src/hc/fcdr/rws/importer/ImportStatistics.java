package hc.fcdr.rws.importer;

import java.util.Date;

public class ImportStatistics
{
    private String importDateTime;
    private Long totalLoadingTime;
    private Long totalProcessingTime;
    private Integer numberOfRecordsProcessed;
    private Integer numberOfInvalidRecords;
    
    public ImportStatistics()
    {
        super();
        this.importDateTime = "";
        this.totalLoadingTime = 0L;
        this.totalProcessingTime = 0L;
        this.numberOfRecordsProcessed = 0;
        this.numberOfInvalidRecords = 0;
    }
    
    public ImportStatistics(String importDateTime, Long totalLoadingTime,
            Long totalProcessingTime, Integer numberOfRecordsProcessed,
            Integer numberOfInvalidRecords)
    {
        super();
        this.importDateTime = importDateTime;
        this.totalLoadingTime = totalLoadingTime;
        this.totalProcessingTime = totalProcessingTime;
        this.numberOfRecordsProcessed = numberOfRecordsProcessed;
        this.numberOfInvalidRecords = numberOfInvalidRecords;
    }

    public String getImportDateTime()
    {
        return importDateTime;
    }

    public void setImportDateTime(String importDateTime)
    {
        this.importDateTime = importDateTime;
    }

    public Long getTotalLoadingTime()
    {
        return totalLoadingTime;
    }

    public void setTotalLoadingTime(Long totalLoadingTime)
    {
        this.totalLoadingTime = totalLoadingTime;
    }

    public Long getTotalProcessingTime()
    {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(Long totalProcessingTime)
    {
        this.totalProcessingTime = totalProcessingTime;
    }

    public Integer getNumberOfRecordsProcessed()
    {
        return numberOfRecordsProcessed;
    }

    public void setNumberOfRecordsProcessed(Integer numberOfRecordsProcessed)
    {
        this.numberOfRecordsProcessed = numberOfRecordsProcessed;
    }

    public Integer getNumberOfInvalidRecords()
    {
        return numberOfInvalidRecords;
    }

    public void setNumberOfInvalidRecords(Integer numberOfInvalidRecords)
    {
        this.numberOfInvalidRecords = numberOfInvalidRecords;
    }

    @Override
    public String toString()
    {
        return "ImportStatistics [importDateTime=" + importDateTime
                + ", totalLoadingTime=" + totalLoadingTime
                + ", totalProcessingTime=" + totalProcessingTime
                + ", numberOfRecordsProcessed=" + numberOfRecordsProcessed
                + ", numberOfInvalidRecords=" + numberOfInvalidRecords + "]";
    }
    
    
}
