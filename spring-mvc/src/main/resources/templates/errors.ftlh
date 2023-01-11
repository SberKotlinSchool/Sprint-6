<#import "commons/modal.ftlh" as m />

<@m.modal "Ошибки">

  <h4>Ошибка</h4>
  <div>${status}</div>
  <div>${message}</div>
  <div>${url}</div>
  <div>${method}</div>

</@m.modal>