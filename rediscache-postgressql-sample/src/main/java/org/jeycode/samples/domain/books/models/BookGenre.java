package org.jeycode.samples.domain.books.models;

public enum BookGenre {
  FICTION("Fiction"),
  NON_FICTION("Non-Fiction"),
  MYSTERY("Mystery"),
  ROMANCE("Romance"),
  SCIENCE_FICTION("Science Fiction"),
  FANTASY("Fantasy"),
  THRILLER("Thriller"),
  HORROR("Horror"),
  BIOGRAPHY("Biography"),
  HISTORY("History"),
  SELF_HELP("Self-Help"),
  CHILDREN("Children"),
  POETRY("Poetry"),
  DRAMA("Drama"),
  COMEDY("Comedy");

  private final String displayName;

  BookGenre(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
