# Core Expansion Community Detection with Java and JGraphT
Open-source implementation of Core Expansion algorithm based on primitives of [JGraphT](https://jgrapht.org/). Original algorithm was described in:

Choumane et al., **Core expansion: a new community detection algorithm based on neighborhood overlap**, [Social Network Analysis and Mining 10, 30 (2020)](https://link.springer.com/article/10.1007/s13278-020-00647-6).

## How to use it?
To use it as command line application you need to have Java 11+

1) Clone repository https://github.com/kkudin/core-expansion
```shell
git clone https://github.com/kkudin/core-expansion.git
```

2) Build it with maven
```shell
mvn clean package
```

3) In target folder you can find .jar files, you need jar with dependencies
```
java -jar core-expansion-1.0-SNAPHOT-jar-with-dependencies.jar <arguments>
```

Input file is required option.

Available arguments:
* Comment char (default #) ```-c,--comment```
* Delimiter char (default ,) ```-d,--delimiter```
* Input file (required) ```-i,--inputFile```
* Result directory path (default results) ```-r,--resultPath```

Example
```shell
java -jar core-expansion-1.0-SNAPHOT-jar-with-dependencies.jar -i mygraph.tsv -d \t -c # -r mygraph_results
```

## References
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
