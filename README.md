# excelMerger

## Introduction 

------

**ExcelMerger** is a program written in *Java*. Its sole purpose is to merge data from many excel files into a single excel file. The user can give as many files as he wants as input and specify certain rows and columns (*same for every file*) to be copied into the output. 

------
## Configuration

All the configuration is done via an xml file. Please copy a sample as follows 

~~~~
$ cp application.properties.sample.xml application.properties.xml
~~~~

Next define your custom file in the `global.properties` file

~~~~
application.properties.file=application.properties.xml
~~~~

Editing these files should be done with a decent text editor, for windows users [Notepad++](https://notepad-plus-plus.org). 

## Structure

Your configuration file (i.e. `application.properties.xml)` should comply with the `application.properties.xsd` found in the `resources` folder. Have a look there if something is not working as expected. 

### The application.properties.xml

Main element is the `<ExcelFiles>` under which you can find many `<InputFile>` , where you must define all your input excel files and your (one) and one `<OutputFile>` output file where all the configured input files will get merged into. Both of them share these common tags

`<Path>/path/to/my/file/some-file-a.xlsx</Path>`
`<Workbook>the-name-of-the-workbook</Workbook>`

Each `<InputFile>` encapsulates the `<Header>` and `<Content>` element.

#### The `<Header>` element
```xml
        <Header>
            <parse>true</parse>
            <start>A3</start>
            <end>A3</end>
            <autoresize></autoresize>
        </Header>
```
The above snippet states that it will parse a header area of the input file starting from cell A3 and ending at the cell A3. You could also state an ending cell at any cell you wish.

#### The `<Content>` element
```xml
        <Content>
            <parse>true</parse>
            <start>B7</start>
            <end>Q</end>
            <autoresize>
                <columns>A</columns>
                <columns>B</columns>
                <columns>C</columns>
                <columns>D</columns>
                <columns>E</columns>
                <columns>F</columns>
                <columns>G</columns>
                <columns>H</columns>
                <columns>I</columns>
                <columns>J</columns>
                <columns>K</columns>
                <columns>L</columns>
                <columns>M</columns>
                <columns>N</columns>
                <columns>O</columns>
                <columns>P</columns>
            </autoresize>
            <rowsheights>
                <row>2</row>
                <height>1500</height>
            </rowsheights>
        </Content>
```
The above snippet states that it will parse the area B7 to Q column, until Q column reaches last line and after the merging get's done in the output file, it will autoresize the corresponding `<columns>` and will set `<height>` to 1500 of the 2nd row. 


------

 ## License

Each library is released under its own license. This repository is published under [GNU/GPL Version 3](LICENSE).


