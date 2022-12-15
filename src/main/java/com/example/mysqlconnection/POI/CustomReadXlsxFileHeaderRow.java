package com.example.mysqlconnection.POI;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CustomReadXlsxFileHeaderRow {

    private List<List> bookData = null;
    public CustomReadXlsxFileHeaderRow() {
        this.bookData = new ArrayList<List>();
    }

    public void getHeaderRowList(String filename, long headerRowNum, ArrayList<String> headerRowNameList, HashMap<String,Long> columnNameToPosMap ) throws Exception {
        OPCPackage pkg = null;
        try
        {
            System.out.println("get Headrowlist method stated");
            //ArrayList headerRowNameList = new ArrayList();
            //HashMap columnNameToPosMap = new HashMap(); //.put(val, new Long((col+1)));
            // Instantiate the OPCPackage class - a container that holds all of those
            // other objects which together represent the .xlsx file's markup - attach
            // a Reader to it and then extract the Shared Strings Table. In the OOXML
            // file, the contents of a cell may not be stored within a child element
            // of the cell element but rather as an entry in the Shared Strings Table
            // Table and then referenced from the cell element.
            pkg = OPCPackage.open(filename);
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = (SharedStringsTable) r.getSharedStringsTable();
            System.out.println("Shared String table completed and passed to fetchSheetParser"+sst);

            // Get an instance of a class that extends DefaultHandler and which
            // provides the necessary callback methods to handle the various elements
            // of the .xlsx file's markup as it is parsed. Note that the Shared
            // Strings Table referebnce is passed to this method along with the List
            // which will ultimately hold the workbook's data.
            XMLReader parser = fetchSheetParser(sst, this.bookData, headerRowNum, 0, 0 );
            System.out.println("XML parser store the fetch data:"+parser);
            // Get an Iterator from the Reader to allow each worksheet to be processed.
            Iterator<InputStream> sheets = r.getSheetsData();
            System.out.println("get sheets :"+sheets);
            // Process the worksheets one at a time by passing the xml markup for each
            // to the parser.
            if (sheets.hasNext()) {
                InputStream sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                System.out.println("inputSource from streamsheets:"+sheetSource);
                parser.parse(sheetSource);
                sheet.close();
            }

            // Print out the multi-diemnsioanl array which should contain the data
            // extracted from the workbook.
            //
            // First, get an Iterator that allows the data for each sheet to be
            // retrieved.
            Iterator<List> sheetIter = this.bookData.iterator();
            if(sheetIter.hasNext()) {
                //System.out.println("New sheet:");

                // Get the List that contains the data for all of the rows on the
                // sheet...
                List<List> rowList = sheetIter.next();

                // ...and get from that, an Iterator that will allow acces to each
                // rows data.
                Iterator<List> rowIter = rowList.iterator();
                //long pos = -1;
                if (rowIter.hasNext()) {

                    // Get the List that contains the data for a single row on a
                    // worksheet....

                    List<String> cellList = rowIter.next();
                    // ...and an Iterator that will allow access to the data recovered
                    // from the cells on the worksheet.
                    Iterator<String> cellIter = cellList.iterator();
                    String cellVal = "";
                    long cellPos = 0;
                    while(cellIter.hasNext()) {
                        cellPos++;
                        cellVal = (String)cellIter.next();
                        // Simply display the cells data on screen.
                        //System.out.println("[" + cellVal + "]");
                        headerRowNameList.add( cellVal );
                        columnNameToPosMap.put( cellVal, new Long( cellPos) );

                    }
                    cellList.clear();

                }
                rowList.clear();
            }

            //return headerRowNameList;
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
        finally
        {
            if (pkg!=null) {
                try
                {
                    pkg.close();
                }
                catch (Exception e)
                {
                }
            }
        }

    }

    public void getHeaderRowAndPreviewDataList(String filename, long fileDe, long previewDataRowCount, ArrayList<String> headerRowNameList, HashMap<String,Long> columnNameToPosMap, ArrayList<ArrayList<String>> previewDataRowList ) throws Exception {
        OPCPackage pkg = null;
        try
        {
            //ArrayList headerRowNameList = new ArrayList();
            //HashMap columnNameToPosMap = new HashMap(); //.put(val, new Long((col+1)));
            // Instantiate the OPCPackage class - a container that holds all of those
            // other objects which together represent the .xlsx file's markup - attach
            // a Reader to it and then extract the Shared Strings Table. In the OOXML
            // file, the contents of a cell may not be stored within a child element
            // of the cell element but rather as an entry in the Shared Strings Table
            // Table and then referenced from the cell element.
            pkg = OPCPackage.open(filename);
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = (SharedStringsTable) r.getSharedStringsTable();

            // Get an instance of a class that extends DefaultHandler and which
            // provides the necessary callback methods to handle the various elements
            // of the .xlsx file's markup as it is parsed. Note that the Shared
            // Strings Table referebnce is passed to this method along with the List
            // which will ultimately hold the workbook's data.
            XMLReader parser = fetchSheetParser(sst, this.bookData, -1, fileDe, previewDataRowCount );

            // Get an Iterator from the Reader to allow each worksheet to be processed.
            Iterator<InputStream> sheets = r.getSheetsData();

            // Process the worksheets one at a time by passing the xml markup for each
            // to the parser.
            if (sheets.hasNext()) {
                InputStream sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
                sheet.close();
            }

            // Print out the multi-diemnsioanl array which should contain the data
            // extracted from the workbook.
            //
            // First, get an Iterator that allows the data for each sheet to be
            // retrieved.
            Iterator<List> sheetIter = this.bookData.iterator();
            if(sheetIter.hasNext()) {
                //System.out.println("New sheet:");

                // Get the List that contains the data for all of the rows on the
                // sheet...
                List<List> rowList = sheetIter.next();

                // ...and get from that, an Iterator that will allow acces to each
                // rows data.
                Iterator<List> rowIter = rowList.iterator();
                long pos = -1;
                while (rowIter.hasNext()) {
                    pos++;

                    List<String> cellList = rowIter.next();
                    // ...and an Iterator that will allow access to the data recovered
                    // from the cells on the worksheet.
                    Iterator<String> cellIter = cellList.iterator();
                    String cellVal = "";
                    long cellPos = 0;

                    if ( pos == 0 )
                    {
                        while(cellIter.hasNext()) {
                            cellPos++;
                            cellVal = (String)cellIter.next();
                            // Simply display the cells data on screen.
                            //System.out.println("[" + cellVal + "]");
                            headerRowNameList.add( cellVal );
                            columnNameToPosMap.put( cellVal, new Long( cellPos) );

                        }

                    }
                    else
                    {
                        if ( previewDataRowList != null )
                        {
                            ArrayList<String> previewDataRow = new ArrayList<String>();
                            previewDataRowList.add( previewDataRow );
                            while(cellIter.hasNext()) {
                                cellPos++;
                                cellVal = (String)cellIter.next();
                                // Simply display the cells data on screen.
                                //System.out.println("[" + cellVal + "]");
                                previewDataRow.add( cellVal );
                            }
                        }

                    }
                }
                rowList.clear();
            }

            //return headerRowNameList;
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
        finally
        {
            if (pkg!=null) {
                try
                {
                    pkg.close();
                }
                catch (Exception e)
                {
                }
            }
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst,
                                      List<List> bookData1, long headerRowNum, long fileDef, long previewDataRowCount ) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader(
                "org.apache.xerces.parsers.SAXParser");
        ContentHandler handler = new SheetHandler(sst, bookData1, headerRowNum, fileDef, previewDataRowCount );
        parser.setContentHandler(handler);
        return parser;
    }

    private static class SheetHandler extends DefaultHandler {

        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;
        private List<List> sheetData = null;
        private List<String> rowData = null;

        private long fileDef = 0;
        private long headerRowNum = 0;
        private long rowCount = 0;

        private long startRow = 0;
        private long stopRow = 0;
        private long previewDataRowCount = 0;

        private String prevCellref = "";
        private String curCellref = "";

        /**
         * Create an instance of the SheetHandler class using the following
         * parameters
         *
         * @param sst An instance of the SharedStringsTable class that
         *        encapsulates the workbook's shared strings table.
         * @param bookData An instance of a class that implements the List
         *        interface and which will be used to store all of the data
         *        recovered from the workbook.
         */
        public SheetHandler(SharedStringsTable sst, List<List> bookData, long headerRowNum, long fileDef, long previewDataRowCount) {
            this.sst = sst;

            // The data for each sheet will be held in a List, so instantiate
            // that here and add a reference to the bookData List.
            this.sheetData = new ArrayList<List>();
            bookData.add(sheetData);
            this.headerRowNum = headerRowNum;

            this.fileDef = fileDef;
          /*  if ( fileDef != null )
            {
                if ( fileDef.getFirstRowHeaderInd() != null )
                {
                    this.headerRowNum = fileDef.getFirstRowHeaderInd().longValue();
                }
            }*/

            if ( this.headerRowNum <= 0 )
            {
                this.headerRowNum = 1;
            }


            this.previewDataRowCount = previewDataRowCount;
            if (  this.previewDataRowCount < 0 )
            {
                this.previewDataRowCount = 0;
            }
            long maxPreviewCount = 10;
            if ( this.previewDataRowCount > maxPreviewCount )
            {
                this.previewDataRowCount = maxPreviewCount;
            }

            if ( this.previewDataRowCount > 0 )
            {
                this.startRow = this.headerRowNum + 1;
               // if (fileDef!= null && fileDef.getStartRow() != null)
                if(true)
                {
                    //this.startRow = fileDef.getStartRow().intValue();
                    this.startRow=1;
                    if ( this.startRow <= this.headerRowNum )
                    {
                        this.startRow = this.headerRowNum + 1;
                    }
                    this.stopRow = this.startRow + this.previewDataRowCount -1;
                }

            }

        }

        /**
         * Called whenever the parser encounters the start tag of an element
         * within the xml markup.
         *
         * @param uri An instance of the String class that will encapsulate the
         *        name of the Namespace. It will be empty if namespaces are not
         *        used.
         * @param localName An instance of the String class that will
         *        encapsulatethe local name for the element.
         * @param name An instance of the String class that will encapsulate the
         *        qualified name for the element.
         * @param attributes An instance of the Attributes class that will
         *        encapsulate any of the element's attributes. Empty if there
         *        are none.
         * @throws SAXException Thrown if a problem is encountered parsing the
         *         xml markup.
         */
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {

            // The name will be 'row' if this is the start element for a new row
            // within the workbook. If we are dealing with a new row....
            if(name.equals("row")) {
                // Create an ArrayList to hold the data receovered from each cell
                // and add it's reference to the List of rows.
                this.rowCount++;
                this.rowData = null;
                if (this.rowCount == this.headerRowNum
                        || (this.previewDataRowCount >0
                        && (this.startRow == -1 || (this.startRow > 0 && rowCount <= this.stopRow))))
                {
                    this.rowData = new ArrayList<String>();
                    //this.sheetData.add(this.rowData);
                }

                prevCellref = "";
                curCellref = "";
            }
            // If the element marks the start of the markup for a cell...
            else if (name.equals("c")) {
                // Figure out if the value is an index in the SST set a flag
                // to indicate that the Shared Strings Table should be
                // interrogated for the cells contents.
                String cellType = attributes.getValue("t");
                if (!prevCellref.equals(""))
                {
                    prevCellref = curCellref;
                    curCellref = attributes.getValue("r");
                }
                else
                {
                    prevCellref = attributes.getValue("r");
                    curCellref = attributes.getValue("r");
                }

                if (cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }
            }
            // Clear contents cache
            lastContents = "";
        }

        /**
         * Called when the parser encounters the end element tag within the
         * xml markup.
         *
         * @param uri An instance of the String class that will encapsulate the
         *        name of the Namespace. It will be empty if namespaces are not
         *        used.
         * @param localName An instance of the String class that will
         *        encapsulatethe local name for the element.
         * @param name An instance of the String class that will encapsulate the
         *        qualified name for the element.
         * @throws SAXException Thrown if a problem is encountered parsing the
         *         xml markup.
         */
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if (nextIsString) {
                int idx = Integer.parseInt(lastContents);
                //lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                lastContents=sst.getItemAt(idx).toString();
                nextIsString = false;
            }

            //System.err.println( "localName=[" + localName + "] name=["+name+"] lastContents=["+lastContents+"]");
            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("row")) {

                if (this.rowCount == this.headerRowNum )
                {
                    this.sheetData.add(this.rowData);
                }
                else
                {
                    if ( this.previewDataRowCount >0 && this.startRow == -1 )
                    {
                        String cellVal = (String)this.rowData.get(0);
                        if ( cellVal != null )
                        {
                            if ( cellVal.startsWith( "hello" ))
                            {
                                this.startRow = this.rowCount;
                                if ( this.startRow <= this.headerRowNum )
                                {
                                    this.startRow = this.headerRowNum +1;
                                    this.stopRow = this.startRow + this.previewDataRowCount-1;
                                }
                            }
                        }
                    }

                    if ( this.previewDataRowCount >0 && this.startRow > 0 && this.rowCount >= this.startRow && this.rowCount <= this.stopRow )
                    {
                        this.sheetData.add(this.rowData);
                    }
                }
            }
            else if (name.equals("c")) {
                if ( this.rowData != null )
                {
                    // add skipped empty cell val
                    CellReference prevCellrefObj = new CellReference(prevCellref);
                    CellReference curCellrefObj = new CellReference(curCellref);
                    int nextCellIndex = prevCellrefObj.getCol()+1;
                    int curCellIndex = curCellrefObj.getCol();
                    if (curCellIndex > nextCellIndex )
                    {
                        for (int i = nextCellIndex; i < curCellIndex; i++)
                        {
                            this.rowData.add("Column_" + String.valueOf(i+1));
                        }
                    }

                    this.rowData.add(lastContents);
                }
            }

        }

        /**
         * Called to process the elements character data.
         *
         * @param ch An array of type character that contains the character data
         *        recovered from the element.
         * @param start A primitive int whose value indicates the start position
         *        of the character data within the ch array.
         * @param length A primitive int whose value indicates how many
         *        characters were read.
         * @throws SAXException Thrown if a problem is encountered whilst
         *         parsing the xml markup.
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastContents += new String(ch, start, length);
        }
    }

}
