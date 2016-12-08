<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>API</title>
</head>
<body>
	<div>
		<h3 >一、接口调用准备阶段</h3>
		<div>
			<h5 >1、注册</h5>
			<ul >
				<li>登录决策引擎官网<a href="http://？？">http://？？</a>进行注册</li>
				<li>申请接口调用渠道开通？</li>
			</ul>
			<h5 >2、安全约定</h5>
			<ul >
				<li>协议选择：目前阶段提供 http 、 https 协议访问，报文格式采用 JSON格式传输</li>
				<li>采用 MD5 报文摘要算法对报文体进行加密生成报文校验码校验码作为一个报文域由客户端向服务端传输，实现双向 MD5校验，加密报文体包括请求参数和 APPKEY。 APPKEY 是双方约定的应用报文密钥，可定期同步更</li>
				<li>发送请求方，将请求参数、 APPKEY 等要素按照接口要素顺序拼接，通过 MD5 算法获取报文校验码，作为一个请求参数传</li>
				<li>接收报文方，按报文规则接收请求方报文，根据报文校验规则进行安全校验，调用业务模块生成返回要素，按接口要素顺序拼接，并通过 MD5 生成校验码，返回报文请求方</li>
			</ul>
			<h5 >3、服务说明</h5>
			<ul >
				<li>反欺诈评分接口： 根据用户基本信息模型计算客户反欺诈评分</li>
				<li>违约评分：根据用户基本信息模型计算客户违约概率</li>
			</ul>
			<h5 >4、注意事项</h5>
			<ul >
				<li>参数空值规则String 类型和 Integer 类型： 空值可赋值为 null， 将 md5 加密前拼接所得的字符串中的”null”替换为双引号””</li>
				<li>JSON 报文处理请求方需要将所有参数序列化所得的 json 报文外层包一层， 参数名为“request”，最后将该变量作为请求参数</li>
				<li>MD5加密样例：加密内容：数信互融、1234567890、null；加密前：数信互融1234567890；加密结果：83d15f30b81e2f8319418999f778c5d7；</li>
			</ul>
			<h5 >5、在线工具网址参考</h5>
			<ul >
				<li>MD5 在线加密：<a href="http://md5jiami.51240.com">http://md5jiami.51240.com</a></li>
				<li>http 在线接口测试： <a href="http://www.atool.org/httptest.php">http://www.atool.org/httptest.php</a></li>
			</ul>
		</div>
	</div>
	<div>
		<h3 >二、调用反欺诈接口-2.5</h3>
		<div>
			<h5 >1、请求URL</h5>
			<ul >
				<li>正式：</li>
				<li>测试：http://localhost:8080/deis/shared/brmsShared/api/callModelFqz?jsonData=</li>
			</ul>
			<h5 >2、请求参数：请求参数 request是 json 字符串</h5>
			<ul >
				<li>渠道编码，sysSource，String，必填，长度1-50</li>
				<li>业务编码，businessNum，Sting，必填，长度1-30</li>
				<li>机构编码，companyUid，String，必填，长度1-30</li>
				<li>模型编码，type，String，必填，等于71</li>
				<li>决策产品编码，prodId，String，必填，长度32</li>
				<li>客户姓名，name，String，非必填，长度0-60</li>
				<li>电话号码，mobile，String，非必填，长度0或者11</li>
				<li>身份证号，idCard，String，非必填，长度18</li>
				<li>联系人信息，linkmanMobile，JSON，非必填，长度0-500</li>
				<li>公司电话，companyTel，String，非必填，长度0-20</li>
			</ul>
			<h5 >3、响应参数</h5>
			<ul >
				<li>响应状态，status，false 没有获取预期结果，success 返回需要的结果</li>
				<li>对应的返回结果解释，message</li>
				<li>反欺诈评分总分，totalScore</li>
				<li>反欺诈风险等级，riskRanking</li>
				<li>反欺诈建议，finalProposal</li>
				<li>反欺诈触发最高分说明，firstProposal</li>
				<li>反欺诈触发次高分说明，secondProposal</li>
				<li>反欺诈触发第三高分说明，thirdProposal</li>
			</ul>
			<h5 >4、样例</h5>
			<ul >
				<li>请求样例：.....</li>
				<li>响应样例：.....</li>
			</ul>
		</div>
	</div>
	<div>
		<h3 >三、调用违约接口-1.5</h3>
		<div>
			<h5 >1、请求URL</h5>
			<ul >
				<li>正式：</li>
				<li>测试：http://localhost:8080/deis/shared/brmsShared/api/callModelFire?jsonData=</li>
			</ul>
			<h5 >2、请求参数：请求参数 request是 json 字符串</h5>
			<ul >
				<li>渠道编码，sysSource，String，必填，长度1-50</li>
				<li>业务编码，businessNum，Sting，必填，长度1-30</li>
				<li>机构编码，companyUid，String，必填，长度1-30</li>
				<li>模型编码，type，String，必填，等于25</li>
				<li>决策产品编码，prodId，String，必填，长度32</li>
				<li>违约决策表编码，如："A0101","A0102"，String，非必填，长度0-60（编码值可以多个，对应的value值和编码值一致）</li>
			</ul>
			<h5 >3、响应参数</h5>
			<ul >
				<li>响应状态，status，false 没有获取预期结果，success 返回需要的结果</li>
				<li>对应的返回结果解释，message</li>
				<li>违约评分值[452-600]，score</li>
				<li>违约评级[A-G]，crate</li>
				<li>违约概率，wychance</li>
			</ul>
			<h5 >4、样例</h5>
			<ul >
				<li>请求样例：.....</li>
				<li>响应样例：.....</li>
			</ul>
		</div>
	</div>
	
</body>
</html>