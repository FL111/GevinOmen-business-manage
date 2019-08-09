
<html>
  <head>
    <title>登陆</title>
      <style>
          * {
              font-family: "montserrat",sans-serif;
          }
          body {
              margin: 0;
              padding: 0;
              background: #333;
          }

          .form-login {
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
              background: rgba(255,255,255,0.5);
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
      </style>
  </head>
  <body>
  <div class="form-login">
      <form action="http://localhost:8081/user/login" method="post">
          <h1>Welcome</h1>
          <input class="txtb" type="text" name="username"/>
          <br/>
          <input class="txtb" type="password" name="password"/>
          <br/>
          <input class="login-btn" type="submit" value="登陆"/>
      </form>
  </div>
     <br/>

     <br/>

  </body>
</html>
