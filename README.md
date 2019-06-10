# excelMerger

## Introduction 

------

**ExcelMerger** is a program written in *Java*. Its sole purpose is to merge data from many excel files into a single excel file. The user can give as many files as he wants as input and specify certain rows and columns (*same for every file*) to be copied into the output. 

![pseudocode](C:\Users\asyrmatos 1\Desktop\pseudocode.png)

------

## Structure

### The application.properties.xsd file

It handles two elements: The *Header* and the *Content*. This file contains the rules these two methods should obey. 

Firstly, it states that the compiler should look at a certain *Path* to find the excel *Workbook*. 

```java
<xs:element type="xs:string" name="Path"/>
<xs:element type="xs:string" name="Workbook"/>
```

Then it specifies the *type* and *length* of each of the method inputs. These are the beginning and ending points of the data that will be copied from the excel files. 

```java
<xs:restriction base="xs:string">
	<xs:length value="2"/>
</xs:restriction>
```

It also includes restrictions for elements such as the auto resizing, the height and the type of rows and columns. 

```java
<xs:element name="autoresize">
	<xs:complexType>
		<xs:sequence>
			<xs:element type="xs:string" name="columns" minOccurs="0" maxOccurs="5000"/>
		</xs:sequence>
	</xs:complexType>
</xs:element>
```

------

## Sample

A [sample](<https://github.com/dimipet/excelMerger/blob/master/application.properties.sample.xml> "application.properties.sample.xml)") xml file can be found on [GitHub](<https://github.com/dimipet/excelMerger> "excelMerger on GitHub"). Users can download it locally and define it on the [global.properties](<https://github.com/dimipet/excelMerger/blob/master/global.properties>"global.properties on GitHub") file. The editing can be done with a text editor like [Notepad++](<https://notepad-plus-plus.org/download/v7.6.6.html>"Notepad++ Download"). 

------

 ## License



