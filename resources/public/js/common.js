var forum = new function() {
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

  this.updatePage = function(content) {
    if (content.title && (typeof content.title) === 'string')
      $(document).attr('title', content.title);
    if (content.body)
      $('#page').replaceWith(content.body);
    if (content.js)
      content.js.map(this.loadScript);
  }
}
