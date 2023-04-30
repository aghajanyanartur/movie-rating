const getById = document.getElementById('getById');

// Create the input field for entering the movie ID
const movieIdInput = React.createElement('input', { type: 'number', placeholder: 'Enter movie ID', id: 'movieId' });

// Create the button to fetch the movie data
const fetchButton = React.createElement('button', { onClick: fetchMovieData }, 'Get Movie Information');

// Create the container for the input field and the fetch button
const fetchContainer = React.createElement('div', null,
    movieIdInput,
    fetchButton
);

// Create the container to display the movie data
const movieDataContainer = React.createElement('div', { id: 'movieDataContainer' });

// Create the container for both the fetch container and the movie data container
const appContainer = React.createElement('div', null,
    fetchContainer,
    movieDataContainer
);

// Add the app container to the getById element
ReactDOM.render(appContainer, getById);

// Function to fetch movie data and display it
function fetchMovieData() {
    const id = document.getElementById('movieId').value;

    fetch(`/movies/${id}`)
        .then(response => {
            if (!response.ok) {
               throw new Error(response.status);
            }
               return response.json();
        })
        .then((movieData) => {
            const movieDataElement = React.createElement('div', null,
                React.createElement('p', null, `ID: ${movieData.id}`),
                React.createElement('p', null, `Title: ${movieData.title}`),
                React.createElement('p', null, `Genre: ${movieData.genre}`),
                React.createElement('p', null, `Released At: ${movieData.releasedAt}`),
                React.createElement('p', null, `Director: ${movieData.director}`),
                React.createElement('p', null, `Rating: ${movieData.rating}`)
            );

            const clearButton = React.createElement('button', { onClick: clearMovieData }, 'Close');
            const deleteButton = React.createElement('button', { onClick: deleteMovieData }, 'Delete Movie');
            const updateButton = React.createElement('button', { onClick: showUpdateForm }, 'Update Movie');
            const reviewButton = React.createElement('button', { onClick: getReviews }, 'See Reviews');
            const addReviewButton = React.createElement('button', { onClick: showReviewForm }, 'Add Review');
            const reviewFormContainer = React.createElement('div', { id: 'reviewFormContainer' });

            const movieDataContainer = React.createElement('div', null,
                  movieDataElement,
                  clearButton,
                  deleteButton,
                  updateButton,
                  reviewButton,
                  addReviewButton,
                  reviewFormContainer
            );
            ReactDOM.render(movieDataContainer, document.getElementById('movieDataContainer'));
        })
        .catch((error) => {
            console.error('Error fetching movie data:', error);
            if (error.message === "500") {
                alert('Movie not found!');
            }
        });
}

function showReviewForm() {
    const reviewForm = React.createElement('form', { onSubmit: submitReview },
        React.createElement('input', { type: 'number', id: 'reviewId', placeholder: 'Review id', required: true }),
        React.createElement('input', { type: 'number', id: 'userId', placeholder: 'User id', required: true }),
        React.createElement('textarea', { id: 'comment', placeholder: 'Add your review here', required: true }),
        React.createElement('textarea', { id: 'reviewRating', placeholder: 'Rating', min: 0, max: 10, step: 0.1, required: false  }),
        React.createElement('button', { type: 'submit' }, 'Submit')
    );

    ReactDOM.render(reviewForm, document.getElementById('reviewFormContainer'));
}

function submitReview(event) {
    event.preventDefault();

    const id = document.getElementById('reviewId').value;
    const movieId = document.getElementById('movieId').value;
    const userId = document.getElementById('userId').value;
    const description = document.getElementById('comment').value;
    const rating = document.getElementById('reviewRating').value;

    fetch(`/movies/${movieId}/reviews`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id, movieId, userId, description, rating })
    })
    .then(() => {
        clearReviewForm();
        alert('Review added successfully!');
    })
    .catch((error) => {
        console.error('Error adding review:', error);
    });
}

function clearReviewForm() {
    ReactDOM.unmountComponentAtNode(document.getElementById('reviewFormContainer'));
}


function getReviews() {
    const id = document.getElementById('movieId').value;

    fetch(`/movies/${id}/reviews`)
        .then(response => {
            if (!response.ok) {
               throw new Error(response.status);
            }
            return response.json();
        })
        .then((reviewsData) => {
            const reviewsList = reviewsData.map((review) => {
                return React.createElement('li', null, `${review.username}: ${review.comment}`);
            });

            const reviewsContainer = React.createElement('div', null,
                React.createElement('h2', null, 'Reviews'),
                React.createElement('ul', null, reviewsList),
                React.createElement('button', { onClick: clearReviews }, 'Close')
            );

            ReactDOM.render(reviewsContainer, document.getElementById('reviewsContainer'));
        })
        .catch((error) => {
            console.error('Error fetching reviews data:', error);
            if (error.message === "500") {
                alert('No reviews found!');
            }
        });
}

function clearReviews() {
    ReactDOM.unmountComponentAtNode(document.getElementById('reviewsContainer'));
}

function clearMovieData() {
    ReactDOM.unmountComponentAtNode(document.getElementById('movieDataContainer'));
    document.getElementById('movieId').value = '';
}


function deleteMovieData() {
  const id = document.getElementById('movieId').value;

  const confirmed = window.confirm('Are you sure you want to delete this movie?');
  if (confirmed) {
      fetch(`/movies/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: id })
      })
      .then(() => {
        clearMovieData();
      })
      .catch((error) => {
        console.error('Error deleting movie data:', error);
      });

      document.getElementById('movieId').value = '';
  }
}

const updateForm = React.createElement('form', { onSubmit: updateMovieData },

    React.createElement('input', { type: 'text', id: 'updateTitle', placeholder: 'Title', required: false }),
    React.createElement('select', { id: 'updateGenre', required: false },
        React.createElement('option', { value: 'DRAMA' }, 'Drama'),
        React.createElement('option', { value: 'HORROR' }, 'Horror'),
        React.createElement('option', { value: 'COMEDY' }, 'Comedy'),
        React.createElement('option', { value: 'ROMANCE' }, 'Romance')
        ),
    React.createElement('input', { type: 'text', id: 'updateReleasedAt', placeholder: 'Released At', required: false }),
    React.createElement('input', { type: 'text', id: 'updateDirector', placeholder: 'Director', required: false }),
    React.createElement('input', { type: 'number', id: 'updateRating', placeholder: 'Rating', min: 0, max: 10, step: 0.1, required: false }),
    React.createElement('button', { type: 'submit' }, 'Save Changes')
);

function showUpdateForm() {
    const updateFormContainer = React.createElement('div', { id: 'updateFormContainer' }, updateForm);
    ReactDOM.render(updateFormContainer, document.getElementById('movieDataContainer'));
}
function updateMovieData(event) {
    event.preventDefault();
    const id = document.getElementById('movieId').value;
    const updatedMovieData = {
        title: document.getElementById('updateTitle').value,
        genre: document.getElementById('updateGenre').value,
        releasedAt: document.getElementById('updateReleasedAt').value,
        director: document.getElementById('updateDirector').value,
        rating: document.getElementById('updateRating').value
    };
    fetch(`/movies/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedMovieData)
    })
    .then(() => {
        clearMovieData();
        ReactDOM.unmountComponentAtNode(document.getElementById('movieDataContainer'));
    });
}