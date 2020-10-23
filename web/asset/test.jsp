<%--
  Created by IntelliJ IDEA.
  User: 16500
  Date: 2020/10/15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <script type="text/javascript">
        //打印乘法口诀表
        function test () {
            for (var i = 14; i >= 1; i--) {
                for (var z = i; z >= 1; z--) {
                    var factorial = i * z;
                    document.write(i + "x" + z + "=" + factorial + "   " );
                }
                document.write("<br>")
            }
        }
    test();
</script>
</body>
</html>
