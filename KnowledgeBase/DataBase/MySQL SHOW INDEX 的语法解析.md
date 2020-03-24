本文主要讲解一下mysql show index 的语法，以 show index from bbs_posts 为例，先看下执行效果如图

1、Table   表名

2、Non_unique  如果索引不能包括重复值则为0，如果可以则为1。也就是平时所说的唯一索引。

3、Key_name  索引名称，如果名字相同则表明是同一个索引，而并不是重复，比如上图中的第二、三条数据，索引名称都是index_fID_lastTime，其实是一个联合索引。

4、Seq_in_index 索引中的列序列号，从1开始。上图中的二、三条数据，Seq_in_index一个是1一个是2，就是表明在联合索引中的顺序，我们就能推断出联合索引中索引的前后顺序。

5、Column_name 索引的列名。

6、Collation  列以什么方式存储在索引中，大概意思就是字符序。在MySQLSHOW INDEX语法中，有值’A’（升序）或NULL（无分类）。默认的类型是utf8_general_ci，这样的大小写不敏感，比如下面两个sql会出现相同的查询结果：

      select * from Table where content = 'Yes'
      select * from Table where content = 'yes'

    这样可能不符合你的要求，你需要大小写敏感的情况，你可以修改字段字符集类型，如下sql

    alter table bbs_posts modify column content varchar(5000) NOT NULL collate utf8_bin;

    这样修改以后就OK了。

7、Cardinality  基数的意思，表示索引中唯一值的数目的估计值。基数根据被存储为整数的统计数据来计数，所以即使对于小型表，该值也没有必要是精确的。基数越大，当进行联合时，MySQL使用该索引的机会就越大。我们知道某个字段的重复值越少越适合建索引，所以我们一般都是根据Cardinality来判断索引是否具有高选择性，如果这个值非常小，那就需要重新评估这个字段是否适合建立索引。因为MySQL数据库中有各种不同的存储引擎，而每种存储引擎对于B+树索引的实现又各不相同。所以对Cardinality统计时放在存储引擎层进行的，至于它是如何统计更新的在这里就不再做更深入的介绍了。

8、Sub_part 前置索引的意思，如果列只是被部分地编入索引，则为被编入索引的字符的数目。如果整列被编入索引，则为NULL。如上图所示，除了index_content那行显示4外，其他的都是NULL，表明index_content是一个长度为4的前置索引。对于BLOB，TEXT，或者很长的VARCHAR类型的列，必须使用前缀索引，因为MySQL不允许索引这些列的完整长度，这会让索引变得大且慢。选择长度的诀窍在于要选择足够长的前缀以保证较高的选择性，同时又不能太长以便节约空间。下面是计算前置索引长度的一般方法：    

      select count(distinct left(content,3))/count(*) from bbs_posts as sel3

      select count(distinct left(content,4))/count(*) from bbs_posts as sel4

      select count(distinct left(content,5))/count(*) from bbs_posts as sel5

     最后算出来那个长度的基数接近完整列的选择行就OK了，完整列 select count(distinct content)/count(*) from bbs_posts

9、Packed  指示关键字如何被压缩。如果没有被压缩，则为NULL。压缩一般包括压缩传输协议、压缩列解决方案和压缩表解决方案。

10、Null 如果列含有NULL，则含有YES。比如上图中的lastOperateTime其中就包含null，我们知道建立索引的列是不允许为Null的，单列索引不存Null值，复合索引不存全为Null的值，如果列允许为Null，可能会得到“不符合预期”的结果集。我这里是为了更好的给大家展示故意构造了一些数据。

11、Index_type 索引类型，Mysql目前主要有以下几种索引类型：FULLTEXT，HASH，BTREE，RTREE。    

    1). FULLTEXT
    即为全文索引，目前只有MyISAM引擎支持。其可以在CREATE TABLE ，ALTER TABLE ，CREATE INDEX 使用，不过目前只有 CHAR、VARCHAR ，TEXT 列上可以创建全文索引。全文索引并不是和MyISAM一起诞生的，它的出现是为了解决WHERE name LIKE “%word%"这类针对文本的模糊查询效率较低的问题。
    2). HASH
    由于HASH的唯一（几乎100%的唯一）及类似键值对的形式，很适合作为索引。    HASH索引可以一次定位，不需要像树形索引那样逐层查找,因此具有极高的效率。但是，这种高效是有条件的，即只在“=”和“in”条件下高效，对于范围查询、排序及组合索引仍然效率不高。
    3). BTREE    BTREE索引就是一种将索引值按一定的算法，存入一个树形的数据结构中（二叉树），每次查询都是从树的入口root开始，依次遍历node，获取leaf。这是MySQL里默认和最常用的索引类型。
    4). RTREE
    RTREE在MySQL很少使用，仅支持geometry数据类型，支持该类型的存储引擎只有MyISAM、BDb、InnoDb、NDb、Archive几种。相对于BTREE，RTREE的优势在于范围查找。

12、 Comment Index_comment  注释的意思。







