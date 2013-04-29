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

  // var searchMap = {};
  // var selectThread = function(item) {
  //   var tid = searchMap[item];
  // };
  // var populateSearch = function(resp, cb) {
  //   var data = JSON.parse(resp);
  //   var matches = [];
  //   searchMap = {};
  //   var results = data.map(function(item) {
  //     matches.push(item.title);
  //     searchMap[item.title] = item.tid;
  //     return item.title;
  //   });
  //   cb(results);
  // };

  // $(document).ready(function() {
  //   $('input#search').typeahead({
  //     minLength: 3,
  //     updater: selectThread,
  //     source: function(query, process) {
  //       $.ajax({
  //         url : '/thread-search',
  //         type : 'GET',
  //         data : 'query=' + query,
  //         success : function(resp) {
  //           populateSearch(resp, process);
  //         }
  //       });
  //     }
  //   });
  // });
}) ();
