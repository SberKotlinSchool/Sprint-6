<#macro logout>
  <div class="flex-container footer-row">
    <form action="/logout" method="post">
      <input type="submit" id="logoutButton" hidden value="Выход" onclick="confirmLogout(event)" />
      <span class="flex-container menu-column">
							<div class="flex-menu-item">
                <label for="logoutButton">
                  <div>
									  <img src="${baseUrl}/static/svg/logout/free-icon-font-exit-3917349.svg" width="30px" height="30px">
								  </div>
                </label>
							</div>
							<div class="flex-menu-item">
								Выход
              </div>
      </span>
    </form>
  </div>
</#macro>
