BrailleTrans for Java 1.0
December 2004
This folder contains the compiled Java classes for BrailleTrans and British English to Braille translation files.
You can find the source code in brailletrans.zip (not in this folder)

Copyright Alasdair King, 1999, 2004.
Released under the GPL. See copying.txt.
alasdairking@yahoo.co.uk
http://personalpages.umist.ac.uk/staff/a.king/

RUNNING BRAILLETRANS
BrailleTrans has some demonstration utilities that do translation and display results. 
Enter the following from the command line:
transt test.txt tester 1
You should see some translated text:
Translation Results
Language256
@X  A C E G H K   5 4 3 2 1   A@ A1@ A11@ @A @1A @11A   AX 2@ 3@ @2 @3 XA   AX A 4@ A3X A44@ XA @4A X3A @44A   T @ U V   X@
LanguageInteger
@X  A C E G H K   5 4 3 2 1   A@ A1@ A11@ @A @1A @11A   AX 2@ 3@ @2 @3 XA   AX A 4@ A3X A44@ XA @4A X3A @44A   T @ U V   X@
LanguageUnicode
@X  A C E G H K   5 4 3 2 1   A@ A1@ A11@ @A @1A @11A   AX 2@ 3@ @2 @3 XA   AX A 4@ A3X A44@ XA @4A X3A @44A   T @ U V   X@
(You can open transt.bat to see the Java class you're calling)
If you don't see this, you don't have the right files, you're in the wrong directory, or your Java isn't correctly configured.
You can use transw to see the output in a Java window: this supports Unicode correctly (when your terminal may not)
You can do British English to American Braille Code with the britishtobr files and any text file:
transw myfile.txt britishbr 1

CONTENTS
This is the BrailleTrans system for converting text to Braille and vice versa. You have the following:
- BrailleTrans text-to-Braille library
uk\ac\umist\co\brailletrans\*
- Programs for demonstrating the conversion process
uk\ac\umist\co\brailletrans\tests\*
- Programs for creating language files (for Unicode)
uk\ac\umist\co\brailletrans\utils\*
You don't have the code here, just the library classes. 
