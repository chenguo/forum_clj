var forum = new function() {
  this.page = null;
  this.widgets = false;

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
    query.widgets = false;
    $.ajax({
      url : path,
      type : 'GET',
      data : query,
      success : function(resp) {
        cb(resp);
        forum.updatePage(resp);
      }
    });
  };

  this.setPage = function(page) {
    this.page = page;
  };

  this.updateWidgets = function(widgets) {
    if (this.widgets == false) {
      this.widgets = true;
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

  this.updatePage = function(content) {
    if (content.title && (typeof content.title) === 'string')
      $(document).attr('title', content.title);
    if (content.page)
      forum.setPage(content.page);
    if (content.widgets)
      forum.updateWidgets(content.widgets);
    if (content.body)
      $('#page').replaceWith(content.body);
    if (content.js)
      content.js.map(forum.loadScript);
  }
}
