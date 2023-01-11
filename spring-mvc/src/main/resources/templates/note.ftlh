<#import "commons/app_page.ftlh" as p />

<@p.app_page "Запись">

  <h4>Запись</h4>

  <form method="post" action="/app/note/${note.id}/update">
    <div class="flex-container column">
      <div class="text-input">
        <div class="flex-container column">
          <div class="input-title">
            Фамилия, имя, отчество
          </div>
          <input type="text" name="name" placeholder="Введите ФИО..." required autofocus autocomplete="off"
                 value="${note.name}">
        </div>
      </div>
      <div class="text-input">
        <div class="flex-container column">
          <div class="input-title">
            Номер телефона
          </div>
          <input type="tel" name="phone" placeholder="Введите номер..." required  autocomplete="off" pattern="^[+]?[\d]{11,15}$"
                 value="${note.phone}">
        </div>
      </div>
      <div class="text-input">
        <div class="flex-container column">
          <div class="input-title">
            Адрес
          </div>
          <input type="text" name="address" placeholder="Введите адрес..." required autofocus autocomplete="off"
                 value="${note.address}">
        </div>
      </div>
      <div class="text-input">
        <div class="flex-container column">
          <div class="input-title">
            Коммент
          </div>
          <textarea name="comment" autocomplete="off">${note.comment}</textarea>
        </div>
      </div>
      <div class="table-buttons-block flex-container row">
        <div class="buttons-space">
        </div>
        <div class="flex-container buttons-block">
          <div class="flex-container button-row margin-button">
            <a href="/app/list" class="interface-color">
										<span class="flex-container">
											<div class="yellow-button text-button">
												Записи
											</div>
										</span>
            </a>
          </div>
          <div class="flex-container button-row margin-button">
            <input type="submit" id="submitButton" class="interface-color green-button text-button" value="Сохранить"/>
          </div>
        </div>
      </div>
    </div>
  </form>

</@p.app_page>