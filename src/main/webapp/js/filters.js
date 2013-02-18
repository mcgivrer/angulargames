'use strict';

/**
 * Filter wikipedia url to provide right url about <code>game</code>.
 */
app.filter('wikipedia', function() {
	return function(game) {
		return 'http://fr.wikipedia.org/wiki/' + game;
	}
});