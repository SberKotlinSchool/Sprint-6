<#import "commons/app_page.ftlh" as p />

<@p.app_page "Записи">
  <div class="search-buttons-block flex-container row">
    <form method="post" action="/app/filter">
      <div class="search-block flex-container row">
        <#if filter?? && filter?has_content>
          <input type="text" name="filter" autocomplete="off" placeholder="Поиск..." class="search-input" value="${filter}"/>
        <#else>
          <input type="text" name="filter" autocomplete="off"  placeholder="Поиск..." class="search-input"/>
        </#if>

        <div class="submit_wrapper">
          <input type="submit" id="submitButton" hidden value="send"/>
          <label for="submitButton">
										<span class="flex-container interface-color">
											<div class="button yellow-button margin-button">
												<img src="${baseUrl}/static/svg/search/free-icon-font-search-3917132.svg" width="20px" height="16px">
											</div>
										</span>
          </label>
        </div>
        <#if filter?? && filter?has_content>
          <div class="flex-container button-row">
            <a href="/app/list" class="interface-color">
									<span class="flex-container">
										<div class="button red-button margin-button">
											<img src="${baseUrl}/static/svg/cross/free-icon-font-cross-3917188.svg" width="20px" height="16px">
										</div>
									</span>
            </a>
          </div>
        </#if>
      </div>
    </form>
    <div class="search-space"></div>
    <div class="flex-container buttons-block">
      <div class="flex-container button-row">
        <a href="/app/create" class="interface-color">
									<span class="flex-container">
										<div class="button green-button margin-button">
											<img src="${baseUrl}/static/svg/add_user/free-icon-font-user-add-3917698.svg" width="24px" height="20px">
										</div>
									</span>
        </a>
      </div>
    </div>
  </div>
  <div>
    <table>
      <tr>
        <th>ФИО</th>
        <th>Телефон</th>
        <th>Адрес</th>
        <th></th>
        <th></th>
      </tr>
        <#if notes?has_content>
            <#list notes as note>
              <tr>
                <td>${note.name}</td>
                <td>${note.phone}</td>
                <td>${note.address}</td>
                <td>
                  <a href="/app/note/${note.id}" class="interface-color">
									<span class="flex-container">
										<div class="button yellow-button">
											<img src="${baseUrl}/static/svg/edit/free-icon-font-edit-3917361.svg" width="20px" height="16px">
										</div>
									</span>
                  </a>
                </td>
                <td>
                  <a href="/app/note/${note.id}/delete" class="interface-color" target="_self" onclick="confirmDeleted(event)">
									<span class="flex-container">
										<div class="button red-button">
											<img src="${baseUrl}/static/svg/delete_user/free-icon-font-remove-user-3914336.svg" width="20px" height="16px">
										</div>
									</span>
                  </a>
                </td>
              </tr>
            </#list>
        </#if>
    </table>
  </div>
</@p.app_page>