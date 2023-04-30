const root = document.getElementById('root');

// Create the input fields for creating a new movie
const idInput = React.createElement('input', { type: 'number', placeholder: 'Id', id: 'id', required: true });
const titleInput = React.createElement('input', { type: 'text', placeholder: 'Title', id: 'title', required: false });
const genreInput = React.createElement('select', { id: 'genre', required: false },
    React.createElement('option', { value: 'DRAMA' }, 'Drama'),
    React.createElement('option', { value: 'HORROR' }, 'Horror'),
    React.createElement('option', { value: 'COMEDY' }, 'Comedy'),
    React.createElement('option', { value: 'ROMANCE' }, 'Romance')
);
const releasedAtInput = React.createElement('input', { type: 'text', placeholder: 'Released At', id: 'releasedAt', required: false });
const directorInput = React.createElement('input', { type: 'text', placeholder: 'Director', id: 'director', required: false });
const ratingInput = React.createElement('input', { type: 'number', placeholder: 'Rating', id: 'rating', min: 0, max: 10, step: 0.1, required: false });

// Create the create button
const createButton = React.createElement('button', { onClick: createMovie }, 'Create');

// Create the container for the input fields and the create button
const createContainer = React.createElement('div', null,
    idInput,
    titleInput,
    genreInput,
    releasedAtInput,
    directorInput,
    ratingInput,
    createButton
);

// Add the create container to the root element
ReactDOM.render(createContainer, root);

// Function to create a new movie
function createMovie() {
    const id = document.getElementById('id').value;
    if (!id) {
            alert('Please enter an id.');
            return;
        }
    const title = document.getElementById('title').value;
    const genre = document.getElementById('genre').value;
    const releasedAt = document.getElementById('releasedAt').value;
    const director = document.getElementById('director').value;
    const rating = document.getElementById('rating').value;

    const data = {
        id: id,
        title: title,
        genre: genre,
        releasedAt: releasedAt,
        director: director,
        rating: rating
    };

    fetch('/movies', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then((data) => {
                    const movieElem = createMovieElem(data);
                    document.getElementById('movie-list').appendChild(movieElem);

                    return data;
                  });
                  // Clear the input fields
        document.getElementById('id').value = '';
        document.getElementById('title').value = '';
        document.getElementById('genre').value = 'DRAMA';
        document.getElementById('releasedAt').value = '';
        document.getElementById('director').value = '';
        document.getElementById('rating').value = '';
}