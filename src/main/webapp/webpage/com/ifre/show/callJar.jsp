<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html><head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
  <meta name="viewport" content="width=device-width initial-scale=1.0">
  <title>Markdown Preview</title>
  <link rel="icon" type="image/png" href="data:image/png;base64,iVBORw0KGgo=">
  <link rel="stylesheet" href="./css/markdown-preview.min.css">


</head>
<body>
  <article class="markdown-body"><h4 data-source-line="2">调用决策包</h4>
<blockquote data-source-line="4">
<p>1.下载并且在工程中引入决策包</p>
</blockquote>
<blockquote data-source-line="6">
<p>2.决策包依赖drools</p>
</blockquote>
<pre data-source-line="8"><code class="hljs"><span class="hljs-comment">&lt;!--maven变量定义--&gt;</span>
<span class="hljs-tag">&lt;<span class="hljs-title">properties</span>&gt;</span>
	<span class="hljs-tag">&lt;<span class="hljs-title">drools-decisiontables.version</span>&gt;</span>6.2.0.Final<span class="hljs-tag">&lt;/<span class="hljs-title">drools-decisiontables.version</span>&gt;</span>
		<span class="hljs-tag">&lt;<span class="hljs-title">drools-templates.version</span>&gt;</span>6.2.0.Final<span class="hljs-tag">&lt;/<span class="hljs-title">drools-templates.version</span>&gt;</span>
		<span class="hljs-tag">&lt;<span class="hljs-title">drools-verifier.version</span>&gt;</span>6.2.0.Final<span class="hljs-tag">&lt;/<span class="hljs-title">drools-verifier.version</span>&gt;</span>
		<span class="hljs-tag">&lt;<span class="hljs-title">kie-api.version</span>&gt;</span>6.2.0.Final<span class="hljs-tag">&lt;/<span class="hljs-title">kie-api.version</span>&gt;</span>
		<span class="hljs-tag">&lt;<span class="hljs-title">kie-internal.version</span>&gt;</span>6.2.0.Final<span class="hljs-tag">&lt;/<span class="hljs-title">kie-internal.version</span>&gt;</span>
<span class="hljs-tag">&lt;/<span class="hljs-title">properties</span>&gt;</span>
<span class="hljs-comment">&lt;!--meven依赖--&gt;</span>
<span class="hljs-tag">&lt;<span class="hljs-title">dependencies</span>&gt;</span>
    <span class="hljs-tag">&lt;<span class="hljs-title">dependency</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">groupId</span>&gt;</span>org.drools<span class="hljs-tag">&lt;/<span class="hljs-title">groupId</span>&gt;</span>		   <span class="hljs-tag">&lt;<span class="hljs-title">artifactId</span>&gt;</span>drools-decisiontables<span class="hljs-tag">&lt;/<span class="hljs-title">artifactId</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">version</span>&gt;</span>${drools-decisiontables.version}<span class="hljs-tag">&lt;/<span class="hljs-title">version</span>&gt;</span>
    <span class="hljs-tag">&lt;/<span class="hljs-title">dependency</span>&gt;</span>
    <span class="hljs-tag">&lt;<span class="hljs-title">dependency</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">groupId</span>&gt;</span>org.kie<span class="hljs-tag">&lt;/<span class="hljs-title">groupId</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">artifactId</span>&gt;</span>kie-api<span class="hljs-tag">&lt;/<span class="hljs-title">artifactId</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">version</span>&gt;</span>${kie-api.version}<span class="hljs-tag">&lt;/<span class="hljs-title">versio</span>&gt;</span>
    <span class="hljs-tag">&lt;/<span class="hljs-title">dependency</span>&gt;</span>
    <span class="hljs-tag">&lt;<span class="hljs-title">dependency</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">groupId</span>&gt;</span>org.kie<span class="hljs-tag">&lt;/<span class="hljs-title">groupId</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">artifactId</span>&gt;</span>kie-internal<span class="hljs-tag">&lt;/<span class="hljs-title">artifactI</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">version</span>&gt;</span>${kie-internal.version}<span class="hljs-tag">&lt;/<span class="hljs-title">version</span>&gt;</span>
    <span class="hljs-tag">&lt;/<span class="hljs-title">dependency</span>&gt;</span>
    <span class="hljs-tag">&lt;<span class="hljs-title">dependency</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">groupId</span>&gt;</span>org.drools<span class="hljs-tag">&lt;/<span class="hljs-title">groupId</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">artifactId</span>&gt;</span>drools-templates<span class="hljs-tag">&lt;/<span class="hljs-title">artifactId</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">version</span>&gt;</span>${drools-templates.version}<span class="hljs-tag">&lt;/<span class="hljs-title">version</span>&gt;</span>
    <span class="hljs-tag">&lt;/<span class="hljs-title">dependency</span>&gt;</span>
    <span class="hljs-tag">&lt;<span class="hljs-title">dependency</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">groupId</span>&gt;</span>org.drools<span class="hljs-tag">&lt;/<span class="hljs-title">groupId</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">artifactId</span>&gt;</span>drools-verifier<span class="hljs-tag">&lt;/<span class="hljs-title">artifactId</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">version</span>&gt;</span>${drools-verifier.version}<span class="hljs-tag">&lt;/<span class="hljs-title">version</span>&gt;</span>
    	<span class="hljs-tag">&lt;<span class="hljs-title">exclusions</span>&gt;</span>
    		<span class="hljs-tag">&lt;<span class="hljs-title">exclusion</span>&gt;</span>
    		    <span class="hljs-tag">&lt;<span class="hljs-title">artifactId</span>&gt;</span>xstream<span class="hljs-tag">&lt;/<span class="hljs-title">artifactId</span>&gt;</span>
    		    <span class="hljs-tag">&lt;<span class="hljs-title">groupId</span>&gt;</span>com.thoughtworks.xstream<span class="hljs-tag">&lt;/<span class="hljs-title">groupId</span>&gt;</span>
    		<span class="hljs-tag">&lt;/<span class="hljs-title">exclusion</span>&gt;</span>
    	<span class="hljs-tag">&lt;/<span class="hljs-title">exclusions</span>&gt;</span>
    <span class="hljs-tag">&lt;/<span class="hljs-title">dependency</span>&gt;</span>
<span class="hljs-tag">&lt;/<span class="hljs-title">dependencies</span>&gt;</span></code></pre><blockquote data-source-line="51">
<p>3.在java中调用，拷贝如下代码，然后按照需求传入参数</p>
</blockquote>
<pre data-source-line="53"><code class="hljs"><span class="hljs-comment">//初始化实例</span>
ScoreModelObj defaultCardsModelClient = <span class="hljs-keyword">new</span> ScoreModelObj();
Map&lt;String, String&gt; defaultMap = <span class="hljs-keyword">new</span> HashMap&lt;String, String&gt;();
LoanApplication	defaultCardsObj = <span class="hljs-keyword">new</span> LoanApplication();
<span class="hljs-comment">//传递参数	</span>
<span class="hljs-comment">//dostirng 需要自己填写的部分</span>
defaultMap.put(<span class="hljs-string">"age"</span>, <span class="hljs-string">"25"</span>);				
LoanApplication out = defaultCardsModelClient.getPricingObj(defaultMap, defaultCardsObj);
<span class="hljs-comment">//out中的内容为返回结果</span></code></pre></article>


</body></html>