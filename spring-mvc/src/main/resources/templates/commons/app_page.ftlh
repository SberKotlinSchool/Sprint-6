<#import "logout.ftlh" as l>

<#macro app_page title>
  <!DOCTYPE html>
  <html lang="ru">

  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=0">
    <!-- icon -->
    <link rel="shortcut icon" href="${baseUrl}/static/svg/logo/free-icon-font-address-book.svg" type="image/png">

    <title>${title}</title>
    <link rel="stylesheet" href="${baseUrl}/static/css/styles.css">
    <script src="${baseUrl}/static/js/commons.js" defer></script>
  </head>

  <body>
  <div class="main">
    <div class="main-margin">
      <header class="flex-container row interface-color">
        <div class="logo">
          <a href="/app/list" class="interface-color">
            <img src="${baseUrl}/static/svg/logo/free-icon-font-address-book.svg" width="40px" height="42px">
          </a>
        </div>
        <div class="logo-text">
          ADDRESS BOOK
        </div>
        <div class="header-space">
        </div>
        <div class="flex-container self">

						<span class="flex-container menu-column">
              <div class="flex-menu-item">
								<div>
                  <img src="${baseUrl}/static/svg/person/free-icon-font-user.svg" width="30px" height="30px">
								</div>
							</div>
              <div class="flex-menu-item">${userName}</div>
						</span>

        </div>

      </header>

      <div class="content interface-color">
          <#nested>
      </div>

      <footer class="flex-container">
        <div class="flex-container footer-row">
          <a href="/app/list" class="interface-color">
						<span class="flex-container menu-column">
							<div class="flex-menu-item">
								<div>
                  <img src="${baseUrl}/static/svg/persons/free-icon-font-users-alt.svg" width="30px" height="30px">
								</div>
							</div>
              <div class="flex-menu-item">Записи</div>
						</span>
          </a>
        </div>
        <@l.logout/>
      </footer>
    </div>
  </div>
  </body>

  </html>
</#macro>
