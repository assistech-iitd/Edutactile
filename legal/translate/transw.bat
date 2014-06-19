@echo off
REM
REM This batch file calls the OutputTest class which displays to a Java
REM window the results of translation via Language256, LanguageInt and 
REM LanguageUnicode. This has the advantage of showing Unicode 
REM characters correctly. 
REM
java -classpath . uk.ac.umist.co.brailletrans.tests.OutputTest %1 %2 %3