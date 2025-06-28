// hash.js
import bcrypt from 'bcrypt';

const password = '1230';

bcrypt.hash(password, 10, (err, hash) => {
  if (err) {
    console.error("❌ Error al hashear:", err);
  } else {
    console.log("✅ Hash generado:");
    console.log(hash);
  }
});
