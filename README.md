# excelMerger

## Introduction 

------

**ExcelMerger** is a program written in *Java*. Its sole purpose is to merge data from many excel files into a single excel file.  It is ideal to batch merge same structured xls/xlsx files to one output. The user can give as many files as he wants as input and specify certain rows and columns (*same for every file*) to be copied into the output. 

## Prerequisites
* Java JDK 8
* JAVA_HOME set
* Maven for building

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

If you don't create your own file as stated above, the provided `application.properties.sample.xml`will be used, which probably won't work for your purposes.

Editing these files should be done with a decent text editor. For windows users [Notepad++](https://notepad-plus-plus.org). 

## Structure

Your configuration file (i.e. `application.properties.xml`) should comply with the `application.properties.xsd` found in the `resources` folder. Have a look there if something is not working as expected. 

### The application.properties.xml

The main element is the `<ExcelFiles>` under which you can find many `<InputFile>`, where you can define all your input excel files and your (one) `<OutputFile>` output file where all the configured input files will get merged into. Both of them share these common tags

`<Path>/path/to/my/file/some-file-a.xlsx</Path>`

`<Workbook>the-name-of-the-workbook</Workbook>`

Each `<InputFile>` encapsulates the `<Header>` and the `<Content>` elements.

#### The `<Header>` element
```xml
        <Header>
            <parse>true</parse>
            <start>A3</start>
            <end>A3</end>
            <autoresize></autoresize>
        </Header>
```
The above snippet states that it will parse a header area of the input file starting from cell A3 and ending at cell A3. You can also define an ending cell at any cell you wish.

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
The above snippet states that it will parse the area between cell B7 and column Q. When column Q reaches its last line and after the merging is done in the output file, it will autoresize the corresponding `<columns>` and will set the `<height>` of the 2nd row to 1500. 


------

 ## License

Each library is released under its own license. This repository is published under [GNU/GPL Version 3](LICENSE).


