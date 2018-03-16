ALTER TABLE public.identity ADD creator_id CHAR(36) NULL;
ALTER TABLE public.identity
  ADD CONSTRAINT identity_creator_fk
FOREIGN KEY (creator_id) REFERENCES identity ON DELETE CASCADE ON UPDATE SET NULL;