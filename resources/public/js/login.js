(function() {
  var login = function(event) {
    if (event) event.stopPropagation();
    var user = $("input[name='username']")[0];
    var pw = $("input[name='password']")[0];
    var path = $(location).attr('pathname');

    var query = { 'login-user': user.value,
                  'login-pw': $.md5(pw.value) };
    forum.loadPage(path, query, loginResp);
  }

  var loginResp = function(resp) {
    if (resp.page == 'login') {
      $('label#notification').html('Login failed, please try again')
        .css('display', 'inherit');
    }
  }

  var init = function() {
    forum.setPage('login');

    // Check the remember me box by default
    $('input#remember').prop('checked', true);
    $('button#signin').click(login);
    $('form.login_form').keyup(function(key) {
      var win = $(window);
      var doc = $(document);
      if (key.keyCode == 13 && !key.altKey && !key.ctrlKey)
        login();
    });
  }

  if ($(document)[0].readyState != 'loading')
    init();
  else
    $(document).ready(init);

}) ();
