var forum = new function() {
  this.page = null;
  this.widgets = false;
  this.path = null;

  $(window).on('popstate', function(e) {
    var path = $(location).attr('pathname');
    forum.loadPage(path, {}, function() {});
  });

  this.signOut= function(){

  }

  this.loadScript = function(jsFile) {
    var loaded = false;
    $('script').each(function(idx, script) {
      var a = script;
      if (script.attributes.src.value == jsFile)
        loaded = true;
    });

    if (!loaded) {
      var script   = document.createElement("script");
      script.type  = "text/javascript";
      script.src   = jsFile;
      document.body.appendChild(script);
    }
  };

  this.loadPage = function(path, query, cb) {
    forum.path = path;
    forum.setPageHistory();

    query.widgets = false;
    $.ajax({
      url : path,
      type : 'GET',
      data : query,
      success : function(resp) {
        if (cb) cb(resp);
        forum.updatePage(resp);
      }
    });
  };

  this.setPage = function(page) {
    forum.page = page;
  };

  this.updateWidgets = function(widgets) {
    if (forum.widgets == false) {
      forum.widgets = true;
      for (var key in widgets) {
        var widget = widgets[key];
        var loaded = $('.' + key);

        if (loaded.length > 0) {
          $('#' + key).replace(widget);
        } else {
          $('body').append(widget);
        }
      }
    } else {
    }
  };

  this.setPageHistory = function(path) {
    path = path || forum.path;
    var pathname = $(location).attr('pathname');
    if (path != pathname) {
      forum.path = path
      history.pushState(null, null, path);
    }
  };

  this.setPageHistoryLogin = function() {
    history.replaceState(null, null, 'login');
  }

  this.updatePage = function(content) {
    if (content.title
        && (typeof content.title) == 'string'
        && content.page
        && content.body) {

      if (content.page == 'login')
        forum.setPageHistoryLogin();
      $(document).attr('title', content.title);
      forum.setPage(content.page);
      $('#page').replaceWith(content.body);

      if (content.widgets)
        forum.updateWidgets(content.widgets);
      if (content.js)
        content.js.map(forum.loadScript);
    }
  }
}
