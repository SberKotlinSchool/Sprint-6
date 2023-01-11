<#import "commons/modal.ftlh" as m />

<@m.modal "Вход">
  <h4>Вход</h4>
  <form method="post" action="/login/auth">
    <div class="flex-container column">
      <div class="text-input login-input">
        <div class="flex-container column">
          <div class="input-title">
            Логин
          </div>
          <input type="text" name="login" placeholder="" required autofocus autocomplete="off">
        </div>
      </div>
    </div>
    <div class="flex-container column">
      <div class="text-input login-input">
        <div class="flex-container column">
          <div class="input-title">
            Пароль
          </div>
          <input type="password" name="password" placeholder="" required autocomplete="off">
        </div>
      </div>
    </div>
    <br>
    <div class="flex-container column">
      <div class="text-input login-input">
        <div class="flex-container column">
          <div class="input-title">
          </div>
        </div>
      </div>
    </div>
    <br>
    <div class="flex-container button-row margin-button">
      <input type="submit" id="submitButton" class="interface-color green-button text-button" value="Войти"/>
    </div>
  </form>
</@m.modal >

