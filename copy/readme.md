拆分文件shell

split  -b 1m file-path prefix

参数配置

```
-l -num 此两个选项等价，都用于指定切割成小文件的行数
-b  按照指定大小进行切割
-C  按照指定大小，但是尽量保证每行的完整性
-d  指定数字形式的文件后缀
```

split -3 line line.  #拆分后的小文件的名字为line.开头
 
split -b 80m line line. #按照大小切割文件

split -C 80m line line. #按照大小，尽量维持每行的完整性

split -b 10m -d line line_ # 按照大小拆分，拆分后的小文件名字是数字为准

默认拆分后的文件是以a*等字符为结尾


