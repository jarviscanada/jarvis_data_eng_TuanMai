# Core Java Grep Application

## Introduction
The Core Java Grep Application is an implementation in Java of the `grep` command-line utility. 
The application searches for a text pattern *recursively* in a given directory, and output matched
lines to a file. This integration uses various Java I/O's such as FileReader, FileOutputStream, 
BufferedReader, BufferedWriter, OutputStreamWriter. These were learned and utilized to aid with 
reading lines from a file as well as writing to another file. Further implementation includes 
extending to Lambda expressions for code readability and compactness. 

## Usage
This Grep application requires three program arguments: regex, rootPat, and outFile; where: 

`regex` - Is the specific regular expression pattern to search for
`rootPath` - Is the root path for the folder
`outFile` - Is the output file that stores the results in

Essentially Search `.*IllegalArgumentException.*` pattern from `./grep/src` folder recursively 
and output the result to `/tmp/grep.out` file.

## Pseudocode
Pseudocode for the `process` method:

```java
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

The process method is the method that will begin recursively searching the root path for lines 
matching the regex pattern and will save all those lines into an outfile. 

## Performance Issues
Currently, this application may have performance issues when tackling large files as most of the 
processes are through memory which may lead to memory issues and memory leaks. 

## Improvements
1. To improve the applications current memory issues, Java Streams could be used to store data as
streams are conceptually fixed data structure which elements are stored and computed on demand. 
2. Instead of program arguments being set before running the application, allow dynamic input by 
the user during running of the application. 
3. Allow users to decide whether to search recursively and/or set a limit of memory usage the application
can use.