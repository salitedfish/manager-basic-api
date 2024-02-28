package com.ruoyi.web.controller.system;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.springframework.web.bind.annotation.*;

import javax.print.PrintService;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Hashtable;

/**
 * 调用打印机服务
 */
@Api("调用打印机服务")
@RestController
@RequestMapping("/test/printer")
public class SysPrinterController extends BaseController {
    // 模板字符串
    private String templateStr = "<div style=\"width: 6cm; height: 4cm;\"></div>";

    // 打印纸宽度英寸*72
    private Integer pageWidth = 3 * 72;
    // 打印纸高度英寸*72
    private Integer pageHeight = 2 * 72;

    // 打印机名称
//    private String printerName = "\\\\192.168.0.17\\ZDesigner GX430t";
    private String printerName = "ZDesigner GX430t (副本 1)";

    // 打印的pdf地址
    private String pdfPath = "D:\\lscf\\test.pdf";

    // 字体文件存放地址
    private String fontPath = System.getProperty("user.dir") + "\\ruoyi-admin\\src\\main\\resources\\fonts\\msyhbd.ttc,0";

    /**
     * 打印机调用测试
     */
    @ApiOperation("打印机调用测试")
    @PostMapping ("/test")
    public AjaxResult printTest() throws Exception {
        this.htmlStrToPdf("<div data-v-e787917e=\"\" class=\"html-template\" style=\"width: 6cm; height: 4cm;\"><div data-v-e787917e=\"\" class=\"vdr inactive  template-shortUrl\" style=\"z-index: auto; top: 54px; left: 128px; position: absolute;\"><div class=\"content-container\" style=\"width: 86px; height: 86px;\"><img data-v-e787917e=\"\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFQAAABUCAYAAAAcaxDBAAADQUlEQVR4Xu3c0W4CMQxEUfj/j6b0cYPE0ciBpXQq9SmN41yP7ewGer1cLrf779t+brfny12v14Mv079f7b16o7/eF+hGygW6EeavqQJ9NVDVrHT9tCau9lUD5e90fe13tf+gUDmoBQQktV+gC9GpQgq0QI/HpjUlpZA1xTVfKS9Fy590fdlL98caunvBAl2eXNSUUoWkTUwBTteXvSp0ebSdlpA/D3Sq2CoUNSRVWIEW6HMCqULU5KrQsCkUKAhUocsLZgGRojRfB/t2+YVAgUKhUqTG06YiexrfHVCt93HvQ9NHP22wQMN3BQWKa+GvV6gUMB1PUzKtwbI/9V/zT7/13A2sQMOSIGAal8Km41XolOB6h3aP6Fs/ijP1X03s7O1cC3Qa4uP8At3L81Kgu4He7R1qqGpQesxJL7nUpXf7J3spb97LT1+nFejwE8ZpF04VL0XttleFDh8UUoAPGby7hsohKVglIrWvmix78mf8PlQpJQcLdH20Cr+loaamAKhmyn4VuhD6OqBK8WkKy74U/O715S/PoTQQXmEoBaeKVACm65OHujwNFOghhlVo2GQpsPX1HSeEDqRdWCm727/UHv0r0Odf1k2b3sPruzRi0yaye778TzOmQMOSpACMgbJGoKunx5J0Q1P72p/sa378xl4Rk0NnjwuI/NP8AsWjrgT0UJPTW08toAifPS6FyT/NjxUqg+m4AiR7u08J01NAgW6+8inQAj0m5cel/Pq2STVrOp4CSNfbfa6N1y/Q42flpk3y9I8zpgrQ31ehIhSOfxzQ3TVuukGdC8VbB3XtV/4/+LfWUC2gDQjA7g3Kn93riQ+vQOSwxhVhjStAWr9Aw4P2nwOaHiNerQgpeuqvAsT1VUOnDsoBpXQaoKm/BYr/ICFAajJxwKvQ7FtFyjh2+WkK0YHwA7KrYlKFSXGyLx4FOrx0jA/2ikga0d1NpgoN/w2RAlqg4VdLpzVYXV41VQFb/Tu9hmrDBRqmdIGCQNp0/h1QbVjjStnd86dNbDxfT0rasMYLNOzCBXokwC4vYBr/9woVoOn4tGlNa9zUfwnk9GtkOaiDtx59pwC1/oNA1qa02wFtuECHxL895X8AniST+A28urwAAAAASUVORK5CYII=\" style=\"width: 100%;\"></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-e787917e=\"\" class=\"vdr inactive  template-belongOrgName\" style=\"z-index: auto; top: 98px; left: 0px; position: absolute;\"><div class=\"content-container\" style=\"width: 63px; height: 18px;\"><p data-v-e787917e=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 所属组织 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top:  -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-e787917e=\"\" class=\"vdr inactive  template-cardCode\" style=\"z-index: auto; top: 116px; left: 0px; position: absolute;\"><div class=\"content-container\" style=\"width: 61px; height: 18px;\"><p data-v-e787917e=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 卡片编码 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-e787917e=\"\" class=\"vdr inactive  template-cardName\" style=\"z-index: auto; top: 58px; left: 1px; position: absolute;\"><div class=\"content-container\" style=\"width: 59px; height: 18px;\"><p data-v-e787917e=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 卡片名称 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-e787917e=\"\" class=\"vdr inactive  template-spec\" style=\"z-index: auto; top: 77px; left: 0px; position: absolute;\"><div class=\"content-container\" style=\"width: 63px; height: 18px;\"><p data-v-e787917e=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 规格型号 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-e787917e=\"\" class=\"vdr inactive  template-fixedAssetsCode\" style=\"z-index: auto; top: 34px; left: 0px; position: absolute;\"><div class=\"content-container\" style=\"width: 88px; height: 18px;\"><p data-v-e787917e=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 固定资产编码 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-e787917e=\"\" class=\"vdr inactive  template-c-text1\" style=\"z-index: auto; top: 6px; left: 50px; position: absolute;\"><div class=\"content-container\" style=\"width: 122px; height: 18px;\"><p data-v-e787917e=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 浙江明筑资产标签 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div></div>");
//        this.htmlStrToPdf("<div data-v-f89c5ede=\"\" class=\"html-template\" style=\"width: 6cm; height: 4cm;\"><div data-v-f89c5ede=\"\" class=\"vdr inactive  template-shortUrl\" style=\"z-index: auto; top: 11px; left: 81px; position: absolute;\"><div class=\"content-container\" style=\"width: 70px; height: 70px;\"><img data-v-f89c5ede=\"\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFQAAABUCAYAAAAcaxDBAAAAAXNSR0IArs4c6QAAA0tJREFUeF7t3NtuwzAMA9D2/z+6wx7rADkg5C7pwL26kWiKusTu+nw8Hq/HH/69Xufuns/nG5rp51d7n97qL/oSupHlErqRzF9TJfTThKpmpf7TmrjaVw0U3ql/7Xe1f1CoAMqBCEntl9CF0alCSmgJfR+b1pSUQtYU1/NKeSlaeFL/spfujzV0t8MSury5qCmlCkmbmAKc+pe9KnR5tZ2WkK8ndKrYKhQ1JFVYCS2h5wykClGTq0LDplBCwUAVuhwwixApSs9rsG+XXxgooVCoFKn1tKnIntZ3B1T+bncemr76aYMlNDwrKKG4Fv73CpUCputpSqY1WPan+PX85beeuwkroWFJEGFal8Km61XolMH1Du2VvrpsBpCaUxO7ejvPEpqG9PzzJXQvn48SupvQ9euMqkHpmJNecqlL78YneynfvJefHqeV0OE3jNMunCpeitptrwodviikBB4yeHcNFSApWCUita+aLHvCMz4PVUoJYAldX63C/9JQU1MAVDNlvwpdGPp3hCrFpyks+1LwX/sXXs6hNBBeYSgFp4pUAKb+yYe6PA2U0LcYVqFhk6XA1uM7PhACSLuwUnY3vtQe8ZXQ83/WTZve4fgujdi0iex+XvjTjCmhYUlSAMaEskagq6djSbqhqX3tT/b1fHxir4gJ0NXrIkT49HwJxauuBHSoyemtpxwowlevS2HCp+djhcpguq4Ayd7uKWE6BZTQzVc+JbSEvifl7VL+bj8zpJqp9d1zrfwdam4Jff/ZqmmTvPzrjKkC9PkqVAyF67cjNC3y2u90g5oL5V+DuvYr/KyhcqANiIDdGxSe3f7ED69ABFjrirDWFSD5L6HhoP11hKZjxKcVIUVP8SpA9J9eI8thCb3Z/8tTAeHXFVMB6PPEV4VmP/B7e0IJMLzDksLSqUElLJ5Dp0U+JSzdgOZCEaz9pXg4h8rhClgApuvyJwKr0Kb8+XFYmqLTkjFVrPCu+C5PeW24hN7sl8UUMNXUKhRnASJYGaEAMOUFQOspQAFOFZNOBZpq6F9vSiJM6yV0mDJThU2fHytsOKY15ZcIjgNy9TVyWhKmG1aJ0rrwXn6NLIAqAWnTEWFaF94SKgZREg5nE035jFEp9AeeJJP4uq/HiwAAAABJRU5ErkJggg==\" style=\"width: 100%;\" ></img></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-f89c5ede=\"\" class=\"vdr inactive  template-cardCode\" style=\"z-index: auto; top: 88px; left: 88px; position: absolute;\"><div class=\"content-container\" style=\"width: 57px; height: 18px;\"><p data-v-f89c5ede=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 卡片编码 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-f89c5ede=\"\" class=\"vdr inactive  template-cardName\" style=\"z-index: auto; top: 110px; left: 89px; position: absolute;\"><div class=\"content-container\" style=\"width: 56px; height: 18px;\"><p data-v-f89c5ede=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 卡片名称 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div></div>");
//        this.htmlStrToPdf("<div data-v-2e45168c=\"\" class=\"html-template\" style=\"width: 10cm; height: 10cm;\"><div data-v-2e45168c=\"\" class=\"vdr inactive  template-shortUrl\" style=\"z-index: auto; top: 39px; left: 107px; position: absolute;\"><div class=\"content-container\" style=\"width: 148px; height: 148px;\"><img data-v-2e45168c=\"\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFQAAABUCAYAAAAcaxDBAAAAAXNSR0IArs4c6QAAA0tJREFUeF7t3NtuwzAMA9D2/z+6wx7rADkg5C7pwL26kWiKusTu+nw8Hq/HH/69Xufuns/nG5rp51d7n97qL/oSupHlErqRzF9TJfTThKpmpf7TmrjaVw0U3ql/7Xe1f1CoAMqBCEntl9CF0alCSmgJfR+b1pSUQtYU1/NKeSlaeFL/spfujzV0t8MSury5qCmlCkmbmAKc+pe9KnR5tZ2WkK8ndKrYKhQ1JFVYCS2h5wykClGTq0LDplBCwUAVuhwwixApSs9rsG+XXxgooVCoFKn1tKnIntZ3B1T+bncemr76aYMlNDwrKKG4Fv73CpUCputpSqY1WPan+PX85beeuwkroWFJEGFal8Km61XolMH1Du2VvrpsBpCaUxO7ejvPEpqG9PzzJXQvn48SupvQ9euMqkHpmJNecqlL78YneynfvJefHqeV0OE3jNMunCpeitptrwodviikBB4yeHcNFSApWCUita+aLHvCMz4PVUoJYAldX63C/9JQU1MAVDNlvwpdGPp3hCrFpyks+1LwX/sXXs6hNBBeYSgFp4pUAKb+yYe6PA2U0LcYVqFhk6XA1uM7PhACSLuwUnY3vtQe8ZXQ83/WTZve4fgujdi0iex+XvjTjCmhYUlSAMaEskagq6djSbqhqX3tT/b1fHxir4gJ0NXrIkT49HwJxauuBHSoyemtpxwowlevS2HCp+djhcpguq4Ayd7uKWE6BZTQzVc+JbSEvifl7VL+bj8zpJqp9d1zrfwdam4Jff/ZqmmTvPzrjKkC9PkqVAyF67cjNC3y2u90g5oL5V+DuvYr/KyhcqANiIDdGxSe3f7ED69ABFjrirDWFSD5L6HhoP11hKZjxKcVIUVP8SpA9J9eI8thCb3Z/8tTAeHXFVMB6PPEV4VmP/B7e0IJMLzDksLSqUElLJ5Dp0U+JSzdgOZCEaz9pXg4h8rhClgApuvyJwKr0Kb8+XFYmqLTkjFVrPCu+C5PeW24hN7sl8UUMNXUKhRnASJYGaEAMOUFQOspQAFOFZNOBZpq6F9vSiJM6yV0mDJThU2fHytsOKY15ZcIjgNy9TVyWhKmG1aJ0rrwXn6NLIAqAWnTEWFaF94SKgZREg5nE035jFEp9AeeJJP4uq/HiwAAAABJRU5ErkJggg==\" style=\"width: 100%;\"></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-2e45168c=\"\" class=\"vdr inactive  template-belongOrgName\" style=\"z-index: auto; top: 248px; left: 128px; position: absolute;\"><div class=\"content-container\" style=\"width: 120px; height: 18px;\"><p data-v-2e45168c=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 所属组织 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-2e45168c=\"\" class=\"vdr inactive  template-cardName\" style=\"z-index: auto; top: 189px; left: 125px; position: absolute;\"><div class=\"content-container\" style=\"width: 120px; height: 18px;\"><p data-v-2e45168c=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 卡片名称 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-2e45168c=\"\" class=\"vdr inactive  template-spec\" style=\"z-index: auto; top: 208px; left: 128px; position: absolute;\"><div class=\"content-container\" style=\"width: 120px; height: 18px;\"><p data-v-2e45168c=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 规格型号 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div><div data-v-2e45168c=\"\" class=\"vdr inactive  template-productLineName\" style=\"z-index: auto; top: 228px; left: 129px; position: absolute;\"><div class=\"content-container\" style=\"width: 120px; height: 19px;\"><p data-v-2e45168c=\"\" style=\"text-align: center; line-height: 1.5em; font-size: 14px;\"> 产线名称 </p></div> <div class=\"vdr-stick vdr-stick-tl\" style=\"width: 8px; height: 8px; top: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tm\" style=\"width: 8px; height: 8px; top: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-tr\" style=\"width: 8px; height: 8px; top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-mr\" style=\"width: 8px; height: 8px; margin-top: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-br\" style=\"width: 8px; height: 8px; bottom: -4px; right: -4px;\"></div><div class=\"vdr-stick vdr-stick-bm\" style=\"width: 8px; height: 8px; bottom: -4px; margin-left: -4px;\"></div><div class=\"vdr-stick vdr-stick-bl\" style=\"width: 8px; height: 8px; bottom: -4px; left: -4px;\"></div><div class=\"vdr-stick vdr-stick-ml\" style=\"width: 8px; height: 8px; margin-top: -4px; left: -4px;\"></div></div></div>");
        this.htmlStrToPdf(this.genPrintHtmlStr());
        this.printPdf(this.pdfPath, this.printerName);
        return success("打印完成");
    }

    /**
     * 打印入口
     */
    @ApiOperation("打印入口")
    @PostMapping("/print")
    public AjaxResult print(String printStr) throws Exception {
        // html字符串转pdf
        this.htmlStrToPdf(printStr);
        // 根据打印机名称和pdf路径打印pdf
        this.printPdf(this.pdfPath, this.printerName);
        return success("打印完成");
    }

    /**
     * html字符串转化为pdf
     * @param htmlStr
     * @throws Exception
     */
    public void htmlStrToPdf (String htmlStr) throws Exception {
        // 配置字体文件
        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new DefaultFontProvider();
        FontProgram fontProgram = FontProgramFactory.createFont(this.fontPath);
        fontProvider.addFont(fontProgram);
        converterProperties.setFontProvider(fontProvider);
        // 创建pdf
        OutputStream fileOutputStream = new FileOutputStream(this.pdfPath);
        PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
        // 创建页面
        PdfDocument doc = new PdfDocument(pdfWriter);
        PageSize pageSize = new PageSize( this.pageWidth * 1.38f,this.pageHeight * 1.38f);
        doc.setDefaultPageSize(pageSize);
        // html转pdf
        Document document = HtmlConverter.convertToDocument(htmlStr,doc,converterProperties);
        document.getRenderer().close();
        document.close();
    }

    /**
     * 开始打印pdf
     * @param pdfPath
     * @param printerName
     * @return
     * @throws Exception
     */
    public PDDocument printPdf(String pdfPath, String printerName) throws Exception {
        File file = new File(pdfPath);
        PDDocument document = PDDocument.load(file);
        // 根据打印机名称获取打印机
        PrinterJob job = this.getPrintServiceByName(printerName);
        // 设置打印样式
        this.setPageStyle(document, job);
        // 开始打印
        job.print();
        return document;
    }

    /**
     * 根据打印机名称获取打印机服务
     * @param printerName
     * @return
     * @throws Exception
     */
    public PrinterJob getPrintServiceByName(String printerName) throws Exception{
        PrinterJob job = PrinterJob.getPrinterJob();
        // 遍历查询打印机名称
        boolean flag = false;
        for (PrintService ps : PrinterJob.lookupPrintServices()) {
            String psName = ps.toString();
            System.out.println("搜索到打印机名：" + psName);
            // 选用指定打印机，需要精确查询打印机就用equals，模糊查询用contains
            if (psName.contains(printerName)) {
                flag = true;
                job.setPrintService(ps);
                break;
            }
        }
        if(!flag){
            throw new RuntimeException("打印失败，未找到名称为" + printerName + "的打印机，请检查。");
        }
        return job;
    }

    /**
     * 设置打印参数
     * @param document
     * @param job
     */
    public void setPageStyle(PDDocument document, PrinterJob job) {
        job.setPageable(new PDFPageable(document));
        Paper paper = new Paper();
        // 设置打印纸张大小
        paper.setSize(this.pageWidth,this.pageHeight);
        // 设置边距，单位是像素，10mm边距，对应 28px
        int marginLeft = -14;
        int marginRight = -14;
        int marginTop = -14;
        int marginBottom = -14;
        // 设置打印位置 坐标
        paper.setImageableArea(marginLeft, marginRight, this.pageWidth - (marginLeft + marginRight), this.pageHeight - (marginTop + marginBottom));
        // custom page format
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        // override the page format
        Book book = new Book();
        // append all pages 设置一些属性 是否缩放 打印张数等
        book.append(new PDFPrintable(document, Scaling.SCALE_TO_FIT), pageFormat, 1);
//        book.append(new PDFPrintable(document, Scaling.ACTUAL_SIZE), pageFormat, 1);
        job.setPageable(book);
    }

    // 构建要打印的html字符串
    public String genPrintHtmlStr () throws Exception {
        String resStr = this.templateStr;

        return resStr;
    }

    // 字符串转二维码图片base64
    public String genQRCodeBase64 (String qrCodestr) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.MARGIN, "0");
        //设置二维码图片宽高
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodestr, BarcodeFormat.QR_CODE,600, 600, hints);
        // 写到输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        //转换为base64
        Base64.Encoder encoder = Base64.getEncoder();
        String qrCodeBase64 = "data:image/png;base64," + encoder.encodeToString(outputStream.toByteArray());
        //打印base64结果
        System.out.println("二维码图片:" + qrCodeBase64);
        return qrCodeBase64;
    }
}
