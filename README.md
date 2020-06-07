# core-expansion
Open-source implementation of core-expansion algorithm based on primitives of [JGraphT](https://jgrapht.org/). Original algorithm was described in [Social Network Analysis and Mining 10, 30 (2020)](https://link.springer.com/article/10.1007/s13278-020-00647-6).

If You want to use it in Your work please refer the original paper:
```text
@article{choumane2020core,
  title={Core expansion: a new community detection algorithm based on neighborhood overlap},
  author={Choumane, Ali and Awada, Ali and Harkous, Ali},
  journal={Social Network Analysis and Mining},
  volume={10},
  number={30},
  pages={30},
  year={2020},
  publisher={Springer}
}
```

How to use it?
You need java with version 11+

1) Clone repository https://github.com/kkudin/core-expansion
git clone https://github.com/kkudin/core-expansion.git

2) Build it with maven
mvn clean package

3) In target folder you can find .jar files, you need jar with dependencies
run java -jar *-jar-with-dependencies.jar <arguments>

Input file is required option
Available arguments :
    -c,--comment <arg>      Comment char
    -d,--delimiter <arg>    Delimiter char
    -i,--inputFile <arg>    Input file
    -r,--resultPath <arg>   Result path
