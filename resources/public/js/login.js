(function() {
  var login = function(event) {
    if (event) event.stopPropagation();
    var user = $("input[name='username']")[0];
    var pw = $("input[name='password']")[0];
    var path = $(location).attr('pathname');
    var query = $(location).attr('search').replace(/^\?/,'');

    var test = $.md5(pw.value);

    if (query != '') query += '&';
    query += 'login-user='+user.value+'&login-pw='+$.md5(pw.value);

    $.ajax({
      url : path,
      type : 'GET',
      data : query,
      success : loginResp
    });
  }

  var loginResp = function(resp, status, jqXHR) {
    if (resp.page == 'login') {
      $('label#notification').html('Login failed, please try again')
        .css('display', 'inherit');
    } else {
      forum.updatePage(resp);
    }
  }

  $(document).ready(function() {
    // Check the remember me box by default
    $('input#remember').prop('checked', true);
    $('button#signin').click(login);
    $('form.login_form').keyup(function(key) {
      var win = $(window);
      var doc = $(document);
      if (key.keyCode == 13 && !key.altKey && !key.ctrlKey)
        login();
    });
  });
}) ();
