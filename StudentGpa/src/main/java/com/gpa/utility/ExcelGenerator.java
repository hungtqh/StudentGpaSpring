package com.gpa.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gpa.domain.Student;
import com.gpa.domain.StudentResult;
import com.gpa.domain.Subject;

public class ExcelGenerator {

	public static ByteArrayInputStream allSemesterMarksToExcel(Student student, List<List<Object>> listMarks)
			throws IOException {
		String[] COLUMNs = { "STT", "Mã Môn", "Tên Môn", "TC", "%CC", "%KT", "%Thi", "Điểm CC", "Điểm KT", "Điểm thi",
				"TK(10)", "TK(CH)", "KQ" };

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("StudentMarks");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			int rowIdx = 0;

			// student info
			Row row = sheet.createRow(rowIdx++);
			row.createCell(5).setCellValue("Tên SV:");
			row.createCell(6).setCellValue("");
			row.createCell(7).setCellValue(student.getName());

			row = sheet.createRow(rowIdx++);
			row.createCell(5).setCellValue("Mã SV:");
			row.createCell(6).setCellValue("");
			row.createCell(7).setCellValue(student.getCode());

			row = sheet.createRow(rowIdx++);
			row.createCell(5).setCellValue("Lớp:");
			row.createCell(6).setCellValue("");
			row.createCell(7).setCellValue(student.getStudentClass().getName());

			row = sheet.createRow(rowIdx++);
			row.createCell(5).setCellValue("Ngành:");
			row.createCell(6).setCellValue("");
			row.createCell(7).setCellValue(student.getMajor().getName());

			sheet.createRow(rowIdx++);
			sheet.createRow(rowIdx++);

			/*
			 * semesterName -- 0 
			 * marks -- 1 
			 * gpaInSemester -- 2 
			 * passedCredits -- 3
			 * passedCreditsTillNow-- 4 
			 * gpaTillNow -- 5
			 */

			for (List<Object> semester : listMarks) {

				row = sheet.createRow(rowIdx++);
				row.createCell(4).setCellValue("Học kỳ " + semester.get(0).toString());

				// Row for Header
				Row headerRow = sheet.createRow(rowIdx++);

				// Header
				for (int col = 0; col < COLUMNs.length; col++) {
					Cell cell = headerRow.createCell(col + 2);
					cell.setCellValue(COLUMNs[col]);
					cell.setCellStyle(headerCellStyle);
				}

				Map<Subject, StudentResult> marks = (Map<Subject, StudentResult>) semester.get(1);

				int markIndex = 1;
				for (Map.Entry<Subject, StudentResult> entry : marks.entrySet()) {
					int col = 2;
					Subject subject = entry.getKey();
					StudentResult studentResult = entry.getValue();

					row = sheet.createRow(rowIdx++);
					row.createCell(col++).setCellValue(markIndex++);
					row.createCell(col++).setCellValue(subject.getCode());
					row.createCell(col++).setCellValue(subject.getName());
					row.createCell(col++).setCellValue(subject.getNumberOfCredits());
					row.createCell(col++).setCellValue(subject.getMark1Percent()*100);
					row.createCell(col++).setCellValue(subject.getMark2Percent()*100);
					row.createCell(col++).setCellValue(subject.getMark3Percent()*100);
					row.createCell(col++).setCellValue(studentResult.getMark1());
					row.createCell(col++).setCellValue(studentResult.getMark2());
					row.createCell(col++).setCellValue(studentResult.getMark3());
					row.createCell(col++).setCellValue(studentResult.getAverageMark());
					row.createCell(col++).setCellValue(studentResult.getMarkToChar());

					if (studentResult.getAverageMark() >= 4.0) {
						row.createCell(col++).setCellValue("Đạt");
					} else {
						row.createCell(col++).setCellValue("Không Đạt");
					}
				}
				

				sheet.createRow(rowIdx++);
				
				row = sheet.createRow(rowIdx++);
				row.createCell(4).setCellValue("Điểm trung bình học kỳ hệ 4: " + 
												String.format("%.2f", (float) semester.get(2)));
				
				row = sheet.createRow(rowIdx++);
				row.createCell(4).setCellValue("Điểm trung bình tích lũy (hệ 4): " + 
												String.format("%.2f", (float) semester.get(5)));
				
				row = sheet.createRow(rowIdx++);
				row.createCell(4).setCellValue("Số tín chỉ đạt: " + semester.get(3));
				
				row = sheet.createRow(rowIdx++);
				row.createCell(4).setCellValue("Số tín chỉ tích lũy: " + semester.get(4));
				
				sheet.createRow(rowIdx++);
				sheet.createRow(rowIdx++);
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
}
