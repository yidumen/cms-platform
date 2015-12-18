package com.yidumen.cms.view;

import com.yidumen.cms.entity.Video;
import com.yidumen.cms.entity.VideoClipInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 蔡迪旻
 *         2015年11月28日
 */
public class ClipInfoExcel extends AbstractXlsxStreamingView {
    @Override
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final List<Video> videos = (List<Video>) model.get("videos");
        final Sheet sheet = workbook.createSheet("clipInfo");
        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("编号");
        header.createCell(1).setCellValue("标题");
        header.createCell(2).setCellValue("剪辑来源");
        for (int i = 0, c = videos.size(); i < c; i++) {
            final Video video = videos.get(i);
            final Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(video.getFile());
            row.createCell(1).setCellValue(video.getTitle());
            final StringBuilder clips = new StringBuilder();
            for (final VideoClipInfo clipInfo : video.getClipInfos()) {
                clips.append(clipInfo.getRecording().getFile()).append(", ");
            }
            row.createCell(2).setCellValue(clips.toString());
        }
    }
}
