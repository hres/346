/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2016 Ricardo Mariaca http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * DynamicReports is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with DynamicReports. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package hc.fcdr.rws.reportengine;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;

import hc.fcdr.rws.importer.ImportStatistics;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.column.ComponentColumnBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ImportReport
{
    private ImportStatistics importStatistics;

    public ImportReport(ImportStatistics importStatistics)
    {
        this.importStatistics = importStatistics;
        build();
    }

    private void build()
    {
        StyleBuilder nameStyle = stl.style().bold();
        StyleBuilder valueStyle = stl.style().setHorizontalTextAlignment(
                HorizontalTextAlignment.LEFT);

        FieldBuilder<String> importdatetimeField = field("importdatetime",
                type.stringType());
        FieldBuilder<Long> totalloadingtimeField = field("totalloadingtime",
                type.longType());
        FieldBuilder<Long> totalprocessingtimeField = field(
                "totalprocessingtime", type.longType());
        FieldBuilder<Integer> numberOfRecordsProcessedField = field(
                "numberOfRecordsProcessed", type.integerType());
        FieldBuilder<Integer> numberOfInvalidRecordsField = field(
                "numberOfInvalidRecords", type.integerType());

        VerticalListBuilder nameList = cmp.verticalList(
                cmp.text("Import Date:").setStyle(nameStyle),
                cmp.text("Total Loading Time (sec):").setStyle(nameStyle),
                cmp.text("Total Processing Time (sec):").setStyle(nameStyle),
                cmp.text("Number of Records Processed:").setStyle(nameStyle),
                cmp.text("Number of Invalid Records:").setStyle(nameStyle)
                );
        VerticalListBuilder valueList = cmp.verticalList(
                cmp.text(importdatetimeField).setStyle(valueStyle),
                cmp.text(totalloadingtimeField).setStyle(valueStyle),
                cmp.text(totalprocessingtimeField).setStyle(valueStyle),
                cmp.text(numberOfRecordsProcessedField).setStyle(valueStyle),
                cmp.text(numberOfInvalidRecordsField).setStyle(valueStyle)
                );

        ComponentColumnBuilder nameColumn = col.componentColumn("Name",
                nameList);
        ComponentColumnBuilder valueColumn = col.componentColumn("Value",
                valueList);

        try
        {
            report().setTemplate(Templates.reportTemplate)
                    .setPageFormat(PageType.A5)
                    .fields(importdatetimeField, totalloadingtimeField,
                            totalprocessingtimeField)
                    .columns(nameColumn, valueColumn)
                    .title(Templates.createTitleComponent("Import Report"))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(createDataSource())
                    .toPdf(new FileOutputStream(
                            "/home/zoltanh/report.pdf"));
        }
        catch (DRException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private JRDataSource createDataSource()
    {
        DRDataSource dataSource = new DRDataSource("importdatetime",
                "totalloadingtime", "totalprocessingtime", "numberOfRecordsProcessed", "numberOfInvalidRecords");
        dataSource.add(importStatistics.getImportDateTime(),
                importStatistics.getTotalLoadingTime(),
                importStatistics.getTotalProcessingTime(),
                importStatistics.getNumberOfRecordsProcessed(),
                importStatistics.getNumberOfInvalidRecords()
                );

        return dataSource;
    }

}