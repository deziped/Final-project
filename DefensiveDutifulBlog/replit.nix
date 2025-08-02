{ pkgs }: {
  deps = [
    pkgs.jdk
  ];
  buildInputs = [
    pkgs.openjfx
  ];
}