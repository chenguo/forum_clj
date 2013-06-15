(function() {
  var login = function(event) {
    if (event) event.stopPropagation();
    var user = $("input[name='username']")[0];
    var pw = $("input[name='password']")[0];

    var query = { 'login-user': user.value,
                  'login-pw': $.md5(pw.value) };
    // forum.loadPage(path, query, loginResp);
    $.ajax({
      url : '/login-action',
      type : 'POST',
      data : query,
      dataType : 'JSON',
      success : function(resp) {
        if (resp == true) {
          forum.loadPage('/', {}, null);
        } else {
          $('label#notification')
          .html('Login failed, please try again')
          .css('display', 'inherit');
        }
      }
    });
  }

  var init = function() {
    forum.setPage('login');
    forum.setPageHistoryLogin();

    // Check the remember me box by default
    $('input#remember').prop('checked', true);
    $('button#signin').click(function(a) {
      login();
    });
    $('form.login_form').keyup(function(key) {
      var win = $(window);
      var doc = $(document);
      if (key.keyCode == 13 && !key.altKey && !key.ctrlKey)
        login();
    });
  }

  if ($(document)[0].readyState != 'loading')
    init();
  else {
    $(document).ready(init);
  }

}) ();
