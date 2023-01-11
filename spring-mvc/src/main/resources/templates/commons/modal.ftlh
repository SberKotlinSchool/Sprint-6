<#macro modal title>

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
    <div class="background">
      <div class="modal">
        <div class="modal_container">

          <#nested />

        </div>
      </div>
    </div>

  </body>

  </html>

</#macro>