package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.impl.SsaImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-13.
 */
public class SsaImportHandlerTest extends SubtitleConverterTests {

    private SubtitleImportHandler ssaImportHandler = new SsaImportHandler();

    @Test
    public void testImportTestFile() throws Exception {

        List<SubtitleItem> subtitleItemList = ssaImportHandler.importFile(getFile("test.ssa"), new Configuration());
        assertEquals(5, subtitleItemList.size());

        //mock item
        SubtitleItem mockItem = new SubtitleItem(0, 6000, "[Dotsub\nAny Video Any Language]");
        //check for line break properly handled.
        SubtitleItem subtitleItem = subtitleItemList.get(0);
        assertEquals(mockItem.getStartTime(), subtitleItem.getStartTime());
        assertEquals(mockItem.getDuration(), subtitleItem.getDuration());
        assertEquals(mockItem.getContent(), subtitleItem.getContent());

        mockItem = new SubtitleItem(10000, 5000,
                "[How to lift your video search results higher, while increasing Sponsor and Ad Value]");
        //check for comma properly handled.
        subtitleItem = subtitleItemList.get(2);
        assertEquals(mockItem.getStartTime(), subtitleItem.getStartTime());
        assertEquals(mockItem.getDuration(), subtitleItem.getDuration());
        assertEquals(mockItem.getContent(), subtitleItem.getContent());

        mockItem = new SubtitleItem(18000, 1000, "to communicate worldwide.");
        //check for control codes removed.
        subtitleItem = subtitleItemList.get(4);
        assertEquals(mockItem.getStartTime(), subtitleItem.getStartTime());
        assertEquals(mockItem.getDuration(), subtitleItem.getDuration());
        assertEquals(mockItem.getContent(), subtitleItem.getContent());


        mockItem = new SubtitleItem(7100, 3010, "[Language Powered Video]");
        //check time parsing
        subtitleItem = subtitleItemList.get(1);
        assertEquals(mockItem.getStartTime(), subtitleItem.getStartTime());
        assertEquals(mockItem.getDuration(), subtitleItem.getDuration());
        assertEquals(mockItem.getContent(), subtitleItem.getContent());
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        try {
            ssaImportHandler.importFile(getFile("test.vtt"), new Configuration());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }

}
