package org.jeycode.samples.domain.books.models;

import static java.util.stream.Collectors.toUnmodifiableSet;

import java.util.Set;
import java.util.stream.Stream;

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

  public final String displayName;
  public static final Set<String> SET = Stream.of(values())
      .map(BookGenre::toString)
      .collect(toUnmodifiableSet());

  BookGenre(String displayName) {
    this.displayName = displayName;
  }

}
