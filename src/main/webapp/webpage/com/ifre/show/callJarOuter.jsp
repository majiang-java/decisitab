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
	<pre data-source-line="2"><code class="hljs">IfreClassLoader loader = <span class="hljs-keyword">new</span> IfreClassLoader(Thread.currentThread().getContextClassLoader());
        <span class="hljs-keyword">try</span> {
			Class&lt;?&gt; clazzScoreModelObj = loader.loadClass(<span class="hljs-string">"${packc }.ScoreModelObj"</span>);
			Object instanceScoreModelObj = clazzScoreModelObj.newInstance();
			<span class="hljs-keyword">int</span> SINA_INSTALMENT = <span class="hljs-number">27</span>;
			Map&lt;String, String&gt; defaultMap = <span class="hljs-keyword">new</span> HashMap&lt;String, String&gt;();
			<span class="hljs-comment">// 36 + 50 + 49 + 48 + 55 + 51 + 52 + 42 + 48 + 41 + 42 + 50</span>
			defaultMap.put(<span class="hljs-string">"city"</span>, <span class="hljs-string">"2"</span>);				<span class="hljs-comment">//绍兴市、深圳市</span>
			defaultMap.put(<span class="hljs-string">"bankName"</span>, <span class="hljs-string">"5"</span>);			<span class="hljs-comment">//中国建设银行</span>
			defaultMap.put(<span class="hljs-string">"initPayRatio"</span>, <span class="hljs-string">"0.2"</span>);		<span class="hljs-comment">//首付比例</span>
			defaultMap.put(<span class="hljs-string">"loanMonth"</span>, <span class="hljs-string">"10"</span>);			<span class="hljs-comment">// 贷款期数 </span>
			defaultMap.put(<span class="hljs-string">"relationship"</span>, <span class="hljs-string">"1"</span>);		<span class="hljs-comment">//亲属关系</span>
			defaultMap.put(<span class="hljs-string">"position"</span>, <span class="hljs-string">"1"</span>);			<span class="hljs-comment">//职位</span>
			defaultMap.put(<span class="hljs-string">"industry"</span>, <span class="hljs-string">"-99"</span>);			<span class="hljs-comment">//行业类型</span>
			defaultMap.put(<span class="hljs-string">"sex"</span>, <span class="hljs-string">"1"</span>);					<span class="hljs-comment">//性别</span>
			<span class="hljs-comment">//传入数据			//手机归属地于进件城市是否一致			</span>
			
			LoanApplication defaultCardsObj =  <span class="hljs-keyword">new</span> LoanApplication();
			defaultCardsObj.setType(SINA_INSTALMENT);

			 Object[] _args = <span class="hljs-keyword">new</span> Object[]{defaultMap, defaultCardsObj};
			 Class&lt;?&gt;[] paramsType = <span class="hljs-keyword">new</span> Class[]{Object.class,  LoanApplication.class};
			 
			 Object _instanceLoanApplication = clazzScoreModelObj.getMethod(<span class="hljs-string">"getPricingObj"</span>,paramsType ).invoke(instanceScoreModelObj, _args);

			 defaultCardsObj = (LoanApplication)_instanceLoanApplication;
			 System.out.println(<span class="hljs-string">"totalsocore = "</span> + defaultCardsObj.getTotalScore());
		
			} <span class="hljs-keyword">catch</span> (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} <span class="hljs-keyword">catch</span> (IllegalArgumentException e) {
				e.printStackTrace();
			} <span class="hljs-keyword">catch</span> (InvocationTargetException e) {
				e.printStackTrace();
			} <span class="hljs-keyword">catch</span> (NoSuchMethodException e) {
				e.printStackTrace();
			} <span class="hljs-keyword">catch</span> (SecurityException e) {
				e.printStackTrace();
                        }
                }</code>
                
             </pre>
	</article>
</body>
</html>