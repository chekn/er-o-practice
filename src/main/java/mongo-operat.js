

0\
use m_jar;
use m_test;

1\
db.m_run_data.find({})

m_info
uniqueName    -> name
state         -> state
compartorStr  -> compartorStr
date          -> date

2\
db.m_run_data.remove({});

3\
db.m_run_data.find({});

4\
db.m_run_data.find({}).count()

5\
db.m_run_data.find({"uniqueName":"BlkRankFloat"}).sort({"startDate":-1});
db.m_run_data.update({"_id" : ObjectId("572af22313176818534b6dac")},{$set:{"_prevRankInfo.003639":9}});
db.m_run_data.find({"_id" : ObjectId("572af22313176818534b6dac")});
db.m_run_data.remove({"_id":{$ne: ObjectId("572af22313176818534b6dac")},"uniqueName":"BlkRankFloat"});

6\
db.m_run_data.find({"uniqueName":"BlkIndexUp"}).sort({ "startDate":-1 }).skip(0).limit(2);
db.m_run_data.remove({"_id":{$ne: ObjectId("57299cdd13176818534b644a")},"uniqueName":"BlkIndexUp"});

//js date 构造方法month 参数从0开始
db.m_run_data.remove({"startDate":{$gte:new Date(2016,04,22,15,00)}});

db.m_run_data.remove({"startDate":{$gte:new Date('2016/04/22 15:00:00')}});

7\
db.m_run_data.find();

8\
blockUpDownTop5
% 分号

9\
db.m_run_data.aggregate([{$group:{_id:"$uniqueName", num_tutorial:{$sum:1}}}]);

10\
# limit是pageSize，skip是第n-1页*pageSize （n-1表示几  第1,2...页）　skip表示跳过  多少条数据
db.m_run_data.find({}).limit(1).skip(1);

11\
var cc=db.m_run_data.remove({"_id":{$ne:ObjectId("57199475fca1701b2cff6acc")}});
console.info(cc.nRemoved);

12\
db.m_run_data.dropIndex("tfq_1")

13\
db.m_run_data.ensureIndex({uniqueName:1,refId:1},{unique:true})

14\
db.m_run_data.ensureIndex({tfq:1},{unique:true,sparse:true})

15\
var lastRcds=db.m_run_data.aggregate([{$group:{_id:"$uniqueName", lastDoc:{$last:"$startDate"}}}]);
while(lastRcds.hasNext()) {
    var unit=lastRcds.next();
    var reUnit={"uniqueName":unit._id,"startDate":unit.lastDoc};
    //console.info(reUnit);
    var rcd=db.m_run_data.find(reUnit);
    if(rcd.hasNext()){
        var doc=rcd.next();
        var changeJsObj=db.m_run_data.remove({_id:{$ne:doc._id},"uniqueName":doc.uniqueName});
        console.info("un:"+doc.uniqueName+",  reserve:"+doc._id+",  remove count:"+changeJsObj.nRemoved);
    }
}

console.info("un:"+",  reserve:"+",  remove count:");

db.parsed_data.find({collection:"StkTest"}).sort({parse_time:-1});

db.m_run_data.remove({s:"csd"});

db.m_run_data.find({"uniqueName":"Top"}).sort({"startDate":-1});

db.m_run_data.remove({"_id":ObjectId("577344781317682dfbee5335")});

