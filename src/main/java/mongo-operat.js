
1\
db.hq_run_data.find({})

hq_info
uniqueName    -> name
state         -> state
compartorStr  -> compartorStr
date          -> date

2\
db.hq_run_data.remove({});

3\
db.hq_run_data.find({});

4\
db.hq_run_data.find({}).count()

5\
db.hq_run_data.find({"uniqueName":"Chg5MinStkTest"}).sort({"startDate":-1});

6\
//设置超时
db.hq_run_data.find({"uniqueName":"Amplitude15Pct"}).sort({ "startDate":-1 });

7\
db.hq_run_data.find();

8\
blockUpDownTop5
% 分号

9\
db.hq_run_data.aggregate([{$group:{_id:"$uniqueName", num_tutorial:{$sum:1}}}]);

10\
# limit是pageSize，skip是第n-1页*pageSize （n-1表示几  第1,2...页）　skip表示跳过  多少条数据
db.hq_run_data.find({}).limit(1).skip(1);
