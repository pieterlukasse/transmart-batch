Annotations (common)
====================

"Annotations" is a general term for platforms (e.g. mRNA platforms) and other
ancillary data that needs to be loaded before the experiment data for a certain
high dimensional type. The annotation is generally not specific to a certain
dataset, and can be shared between two or more similar datasets.

For historical reasons, parameter files named `annotation.params` refer to mRNA
platforms. Other platforms' data types have more descriptive names.

Parameters
----------

- `PLATFORM` -- Mandatory. The identifier for the annotation. The name is
  perhaps too specific. It should generally correspond to the name of the parent
  directory of the parameters file. However, unlike `STUDY_ID`, it is not
  inferred.
- `TITLE` -- Mandatory. A human readable title for the annotation.
- `ANNOTATIONS_FILE` -- Mandatory. Either a full path to a TSV file or the name
  of TSV file under a directory that is a sibling of the parameters file and has
  the same name as the parameters file, minus its extension.
- `ORGANISM` -- The scientific name of the species the annotation is associated
  with. Defaults to _Homo Sapiens_.

<!-- vim: tw=80 et ft=markdown spell:
-->
