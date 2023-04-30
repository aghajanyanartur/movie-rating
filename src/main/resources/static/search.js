const searchForm = document.getElementById('searchForm');

// Create the input fields for search
const searchTitleInput = React.createElement('input', { type: 'text', placeholder: 'Title', id: 'title', required: false });
const searchGenreInput = React.createElement('select', { id: 'genre', required: false },
    React.createElement('option', { value: '' }, '---'),
    React.createElement('option', { value: 'DRAMA' }, 'Drama'),
    React.createElement('option', { value: 'HORROR' }, 'Horror'),
    React.createElement('option', { value: 'COMEDY' }, 'Comedy'),
    React.createElement('option', { value: 'ROMANCE' }, 'Romance')
);
const searchReleasedBefore = React.createElement('input', { type: 'text', placeholder: 'Released Before', id: 'releasedBefore', required: false });
const searchReleasedAfter = React.createElement('input', { type: 'text', placeholder: 'Released After', id: 'releasedAfter', required: false });

// Create the search button
const searchButton = React.createElement('button', { onClick: searchMovie }, 'Search');

// Create the container for the input fields and the search button
const searchContainer = React.createElement('div', null,
    searchTitleInput,
    searchGenreInput,
    searchReleasedBefore,
    searchReleasedAfter,
    searchButton
);

// Add the search container to the searchForm element
ReactDOM.render(searchContainer, searchForm);

function searchMovie() {
  const genre = document.getElementById('genre').value;
  const title = document.getElementById('title').value;
  const releasedBefore = document.getElementById('releasedBefore').value;
  const releasedAfter = document.getElementById('releasedAfter').value;

  let url = '/movies';
  if (genre || title || releasedBefore || releasedAfter) {
    url += '?';
    if (genre) url += `genre=${genre}&`;
    if (title) url += `title=${title}&`;
    if (releasedBefore) url += `releasedBefore=${releasedBefore}&`;
    if (releasedAfter) url += `releasedAfter=${releasedAfter}&`;
    // Remove the trailing '&' character
    url = url.slice(0, -1);
  }

  fetch(url)
    .then((response) => {
      if (!response.ok) throw new Error(response.statusText);
      return response.json();
    })
    .then((data) => {
      const table = document.createElement('table');
      const thead = table.createTHead();
      const row = thead.insertRow();
      row.insertCell().appendChild(document.createTextNode('ID'));
      row.insertCell().appendChild(document.createTextNode('Title'));
      row.insertCell().appendChild(document.createTextNode('Genre'));
      row.insertCell().appendChild(document.createTextNode('Director'));
      row.insertCell().appendChild(document.createTextNode('Release Date'));
      row.insertCell().appendChild(document.createTextNode('Rating'));

      const tbody = table.createTBody();
      data.forEach((movie) => {
        const row = tbody.insertRow();
        row.insertCell().appendChild(document.createTextNode(movie.id));
        row.insertCell().appendChild(document.createTextNode(movie.title));
        row.insertCell().appendChild(document.createTextNode(movie.genre));
        row.insertCell().appendChild(document.createTextNode(movie.director));
        row.insertCell().appendChild(document.createTextNode(movie.releaseDate));
        row.insertCell().appendChild(document.createTextNode(movie.rating));
      });

      const resultsDiv = document.getElementById('searchResults');
      resultsDiv.innerHTML = '';
      resultsDiv.appendChild(table);

      // clear button
      const deleteButton = document.createElement('button');
      deleteButton.textContent = 'Close';
      deleteButton.onclick = function () {
      const inputElements = searchForm.getElementsByTagName('input');
      for (let i = 0; i < inputElements.length; i++) { inputElements[i].value = ''; }
      resultsDiv.innerHTML = '';
      };
            resultsDiv.appendChild(deleteButton);
    })
    .catch((error) => {
      console.error('Error searching for movie:', error);
    });
}