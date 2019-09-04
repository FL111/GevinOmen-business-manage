
<html>
  <head>
    <title>登陆</title>
      <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
      <style>
          * {
              font-family: "montserrat",sans-serif;
          }
          body {
              margin: 0;
              padding: 0;
              background: #333;
          }
          .login-box {
              position: absolute;
              top: 0;
              left: -100%;
              width: 100%;
              height: 100vh;
              background-image: linear-gradient(45deg,#9fbaa8,#31354c);
              transition: 0.4s;
          }
          .login-form {
              position: absolute;
              top: 38%;
              left: 45%;
              color: white;
              text-align: center;
          }
          .login-form h1 {
              font-weight: 400;
              margin-top: 0;
          }
          input::-webkit-input-placeholder {
              color: #aab2bd;
              text-align: center;
          }
          .txtb {
              display: block;
              box-sizing: border-box;
              width: 240px;
              background: rgba(0,0,0,0);
              border: 1px solid white;
              padding: 10px 20px;
              color: #000;
              outline: none;
              margin: 10px 0;
              border-radius: 6px;
              text-align: center;

          }
          .login-btn {
              width: 240px;
              background: #2c3e50;
              border: 0;
              color: white;
              padding: 10px;
              border-radius: 6px;
              cursor: pointer;
              font-size: 17px;
          }
          .hide-login-btn {
              display: inline-block;
              background-image: url("/up111/close.png");
              height: 48px;
              width: 48px;
              color: #FFF;
              position: absolute;
              top: 40px;
              right: 40px;
              cursor: pointer;
              font-size: 24px;
              opacity: .7;
          }
          .show-login-btn {
              display: inline-block;
              position: absolute;
              top: 38%;
              left: 50%;
              transform:  translate(-50%,-50%);
              color: white;
              border: 2px solid;
              padding: 10px;
              cursor: pointer;
              font-size: 25px;
              text-align: center;
          }
          .img {
              display: inline-block;
              height: 24px;
              width: 30px;
              padding: 0px;
          }


          .showed {
              left: 0;
          }
      </style>
  </head>
  <body>
  <#--<div class="login-box">-->
      <#--<form action="/user/login" method="post">-->
          <#--<h1>Welcome</h1>-->
          <#--<input class="txtb" type="text" name="username"/>-->
          <#--<br/>-->
          <#--<input class="txtb" type="password" name="password"/>-->
          <#--<br/>-->
          <#--<input class="login-btn" type="submit" value="登陆"/>-->
      <#--</form>-->
  <#--</div>-->
     <#--<br/>-->

     <#--<br/>-->
  <div class="show-login-btn">
      <img src="/up111/login.png" class="img">
      欢迎
  </div>
  <div class="login-box">
      <form class="login-form" action="/user/login"  method="post">
          <h1>Welcome</h1>
          <input class="txtb" type="text" name="username" placeholder="Username"/>

          <input class="txtb" type="password" name="password" placeholder="Password"/>

          <input class="login-btn" type="submit" value="Login"/>
      </form>
  </div>


  <script type="text/javascript">
      $(".show-login-btn").on("click",function(){
          $(".login-box").toggleClass("showed");
      });
      $(".hide-login-btn").on("click",function(){
          $(".login-box").toggleClass("showed");
      });


  </script>

  </body>
</html>
