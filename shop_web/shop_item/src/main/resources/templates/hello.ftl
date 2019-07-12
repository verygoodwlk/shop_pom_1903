<html>
 <head>
     <title>freemarker的模板页面</title>
 </head>
 <body>
    Hello, ${key}
    <hr/>
    <#if age < 18>
        未成年
        <#elseif age < 40>
        成年
        <#elseif age < 60>
        中年
        <#else>
        老年
    </#if>
    <hr/>
    <#list ages as age>
        ${age}
    </#list>
    <hr/>
    ${date?date}<br/>
    ${date?time}<br/>
    ${date?datetime}<br/>
    ${date?string("yyyy年MM月dd日 HH时mm分ss秒")}<br/>

 </body>
</html>