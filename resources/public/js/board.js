(function() {
  var updater = null;
  var searchLen = 0;
  var updateThreads = function() {
    var query = 'search=' + $('input#search').val();
    $.ajax({
      url : '/thread-search',
      type : 'GET',
      data : query,
      success : searchUpdate
    });
  };

  var searchUpdate = function(resp) {
    if (resp.body)
      $('div.threadlist').replaceWith(resp.body);
  };

  var init = function() {
    var searcher = $('input#search');
    searcher.val('');
    searcher.keyup(function(e) {
      if (this.value.length != searchLen) {
        searchLen = this.value.length;
        clearInterval(updater);
        updater = setInterval(updateThreads, 500);
      }
    });
  }

  if ($(document)[0].readyState != 'loading')
    init();
  else
    $(document).ready(init);

}) ();
