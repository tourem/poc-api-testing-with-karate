Feature: working with movies

  Background:
    * url baseUrl

  Scenario: create a movie and retrieve it

    Given path 'movies'
    And request { title: 'Karate Kid' }
    When method post
    Then status 201
    And match response contains { id: '#notnull' }
    And match header Location == baseUrl + '/movies/' + response.id

    Given path 'movies', response.id
    When method get
    Then status 200
    And match response contains { title: 'Karate Kid' }

    Given path 'movies', '42'
    When method get
    Then status 404

  Scenario: add an actor to a movie

    Given path 'movies'
    And request { title: 'Karate Tiger' }
    When method post
    Then status 201

    * def movieId = response.id

    Given path 'movies', movieId
    When method get
    Then status 200
    And match response contains { title: 'Karate Tiger' }

    Given path 'movies', movieId, 'actors'
    And request { firstName: 'JC', lastName: 'VD' }
    When method post
    Then status 200

    Given path 'movies', movieId
    When method get
    Then status 200
    And match response.actors contains { firstName: 'JC', lastName: 'VD' }

  Scenario Outline: Create multiple movies

    Given path 'movies'
    And request { "id": '<id>', "title": '<title>'}
    When method POST
    Then status 201
    And match $ == {id: '<id>', "title": '<title>', "actors":[]}

    Examples:

      | id | title   |
      | dd892255-907a-4e7f-8ab0-215f9f2b2a51  | Amkoulel enfant peul  |
      | dd892255-907a-4e7f-8ab0-215f9f2b2a52  | Go in action |
      | dd892255-907a-4e7f-8ab0-215f9f2b2a53  | Clean code   |